package epam.pratsaunik.tickets.connection;

import epam.pratsaunik.tickets.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static epam.pratsaunik.tickets.connection.DbConfigurationManager.*;

public class ConnectionPoll {

    private static final int MAX_SIZE = 5;
    private BlockingQueue<ProxyConnection> availableCon = new LinkedBlockingDeque<>();
    private static ConnectionPoll instance;
    private static AtomicBoolean isNotCreated = new AtomicBoolean(true);
    private static AtomicInteger consCount=new AtomicInteger(0);
    private static Lock lock = new ReentrantLock();
    private final static Logger log = LogManager.getLogger();

    private ConnectionPoll() {
        try {
            Class.forName(DbConfigurationManager.getInstance().getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver cannot be found",e);
        }
    }

    public static ConnectionPoll getInstance() {
        if (isNotCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPoll();
                    isNotCreated.set(false);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ProxyConnection getConnection() throws ConnectionException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(DbConfigurationManager.getInstance().getProperty(DATABASE_URL),
                    DbConfigurationManager.getInstance().getProperty(DATABASE_USERNAME),
                    DbConfigurationManager.getInstance().getProperty(DATABASE_PWD));
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        log.debug("CONNECTION POLL:"+connection);
        return new ProxyConnection(connection);
    }

    public Connection retrieveConnection() throws ConnectionException {
        ProxyConnection connection;
        try {
            connection = availableCon.poll(5, TimeUnit.MILLISECONDS);
            if (connection == null) {
                lock.lock();
                if (consCount.get() < MAX_SIZE) {
                    availableCon.add(getConnection());
                    consCount.incrementAndGet();
                }
                lock.unlock();
                connection = availableCon.take();
            }
        } catch (InterruptedException e) {
            throw new ConnectionException(e);
        }
        return connection;
    }

    public void returnConnection(Connection connection) throws ConnectionException {
        if (connection.getClass() == ProxyConnection.class) {
            availableCon.offer((ProxyConnection) connection);
            consCount.decrementAndGet();
            log.info("Connection returned " + connection);
        } else
            throw new ConnectionException();
    }

    public void destroyPoll() throws ConnectionException {
        for (int i = 0; i < consCount.get(); i++) {
            try {
                availableCon.take().reallyClose();
            } catch (InterruptedException e) {
                throw new ConnectionException(e);
            }
        }
    }
}

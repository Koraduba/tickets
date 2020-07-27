package epam.pratsaunik.tickets.connection;

import epam.pratsaunik.tickets.exception.ConnectionException;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


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
            Class.forName(ConfigurationManager.DATABASE_DRIVER_NAME.getProperty());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver cannot be found",e);
        }
    }

    /**
     * Singleton for Connection Pool
     * @return instance of Connection Pool
     */
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

    /**
     * Inner method of Connection Pool to get connection from data base
     * @return connection wrapped by ProxyConnection
     * @throws ConnectionException exception in ConnectionPool
     * @see ProxyConnection
     */
    private ProxyConnection getConnection() throws ConnectionException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error(e);
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(ConfigurationManager.DATABASE_URL.getProperty(),properties);
            Statement statement = connection.createStatement();
            statement.executeQuery("SET NAMES UTF8");
            statement.close();
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        log.debug("CONNECTION POLL:"+connection);
        return new ProxyConnection(connection);
    }

    /**
     * public method providing connection from pool ({@code BlockingQueue}
     * @return connection instance wrapped by ({@code ProxyConnection}
     * @throws ConnectionException exception in ConnectionPool
     * @see BlockingQueue
     */
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
            log.info("Connection retrieved " + connection);
        } catch (InterruptedException e) {
            throw new ConnectionException(e);
        }
        return connection;
    }
    /**
     * public method providing to return connection to pool ({@code BlockingQueue}
     * @throws ConnectionException exception in ConnectionPool
     * @see BlockingQueue
     */
    public void returnConnection(Connection connection) throws ConnectionException {
        if (connection.getClass() == ProxyConnection.class) {
            availableCon.offer((ProxyConnection) connection);
            log.info("Connection returned " + connection);
        } else
            throw new ConnectionException();
    }

    /**
     * closing of all connections in pool
     * @throws ConnectionException exception in ConnectionPool
     */
    public void destroyPoll() throws ConnectionException {
        log.debug(consCount.get());
        int num=consCount.get();
        for (int i = 0; i < consCount.get(); i++) {
            try {
                ProxyConnection connection= availableCon.take();
                connection.reallyClose();
                consCount.decrementAndGet();
            } catch (InterruptedException e) {
                throw new ConnectionException(e);
            }
        }
    }
}

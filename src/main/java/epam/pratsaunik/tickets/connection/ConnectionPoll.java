package epam.pratsaunik.tickets.connection;

import epam.pratsaunik.tickets.exception.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static epam.pratsaunik.tickets.connection.DbConfigurationManager.*;

public class ConnectionPoll {

    private static final int DEFAULT_SIZE = 5;
    private BlockingQueue<ProxyConnection> availableCon;
    private Queue<ProxyConnection> usedCons;
    private static ConnectionPoll instance = null;

    private ConnectionPoll() {
        try {
            Class.forName(DbConfigurationManager.getInstance().getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // FIXME: 1/29/2020
        }
        availableCon = new LinkedBlockingDeque<>();
        usedCons = new ArrayDeque<ProxyConnection>();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            availableCon.add(getConnection());
        }
    }

    public static ConnectionPoll getInstance() {
        if (instance == null) {
            instance = new ConnectionPoll();
        }
        return instance;
    }

    private ProxyConnection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DbConfigurationManager.getInstance().getProperty(DATABASE_URL),
                    DbConfigurationManager.getInstance().getProperty(DATABASE_USERNAME),
                    DbConfigurationManager.getInstance().getProperty(DATABASE_PWD));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ProxyConnection(connection);
    }

    //TODO lock
    public Connection retrieveConnection() {
        ProxyConnection connection = null;
        if (availableCon.size() == 0) {
            connection = getConnection();

        } else {
            try {
                connection = availableCon.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            usedCons.offer(connection);
            System.out.println("Connection retrieved " + connection.toString());
        }
        usedCons.add(connection);
        return connection;
    }

    public synchronized void returnConnection(Connection connection) throws ConnectionException {

        if (connection.getClass() == ProxyConnection.class) {
            usedCons.remove(connection);
            availableCon.offer((ProxyConnection) connection);
            System.out.println("Connection returned " + connection.toString());
        }else
            throw new ConnectionException();
    }

    public int getAvailableConnections() {
        return availableCon.size();
    }

    public void destroyPoll() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            try {
                availableCon.take().reallyClose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

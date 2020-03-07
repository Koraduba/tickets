package epam.pratsaunik.tickets.connection;

import epam.pratsaunik.tickets.exception.ConnectionException;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class ConnectionPollTest {

    private static ConnectionPoll connectionPoll;

    @BeforeClass
    public static void initConnectionPoolTest() {
        connectionPoll = ConnectionPoll.getInstance();
    }
    @AfterClass
    public static void clearConnectionPoolTest() throws ConnectionException {
        connectionPoll.destroyPoll();
    }

    @After
    public void clear () throws ConnectionException {
        connectionPoll.destroyPoll();
    }

    @Test
    public void getInstance() {
        ConnectionPoll connectionPollSecond = ConnectionPoll.getInstance();
        assertSame(connectionPoll, connectionPollSecond);
    }

    @Test
    public void retrieveConnection() throws ConnectionException, SQLException {
        int counter = 0;
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Connection connection = connectionPoll.retrieveConnection();
            connections.add(connection);
        }
        for (Connection connection : connections) {
            connection.close();
        }
        for (int i = 0; i < 5; i++) {
            Connection connection = connectionPoll.retrieveConnection();
            if (connections.contains(connection)) {
                counter++;
            }
        }
        for (Connection connection : connections) {
            connection.close();
        }

        assertEquals(5, counter);
    }

    @Test(expected = ConnectionException.class)
    public void returnConnection() throws SQLException, ConnectionException, IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        Connection connection = DriverManager.getConnection(ConfigurationManager2.DATABASE_URL.getProperty(), properties);
        connectionPoll.returnConnection(connection);
    }

    @Test
    public void destroyPoll() throws ConnectionException {
        Connection connection = connectionPoll.retrieveConnection();
        connectionPoll.returnConnection(connection);
        connectionPoll.destroyPoll();
        Connection connectionNew = connectionPoll.retrieveConnection();
        connectionPoll.returnConnection(connectionNew);
        assertNotSame(connection,connectionNew);
    }
}
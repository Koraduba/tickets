package epam.pratsaunik.tickets.dao;


import epam.pratsaunik.tickets.connection.ConnectionPoll;
import epam.pratsaunik.tickets.exception.ConnectionException;
import epam.pratsaunik.tickets.exception.CustomException;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private Connection connection;

    public EntityTransaction() {
    }

    public void begin(AbstractDAO... daos) {
        if(connection==null){
            connection=ConnectionPoll.getInstance().retrieveConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace(); // FIXME: 1/29/2020
        }
        for (AbstractDAO dao : daos) {
            dao.set(connection);
        }
    }

    public void end() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ConnectionPoll.getInstance().returnConnection(connection);
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        connection=null;
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

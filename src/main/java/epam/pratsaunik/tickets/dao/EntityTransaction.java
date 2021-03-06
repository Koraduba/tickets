package epam.pratsaunik.tickets.dao;


import epam.pratsaunik.tickets.connection.ConnectionPoll;
import epam.pratsaunik.tickets.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private Connection connection;
    private final static Logger log = LogManager.getLogger();

    public EntityTransaction() {
    }

    public void begin(AbstractDAO... daos) {
        if(connection==null){
            try {
                connection=ConnectionPoll.getInstance().retrieveConnection();
            } catch (ConnectionException e) {
               log.fatal("Connection error",e);
               throw new RuntimeException();
            }
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace(); // FIXME: 1/29/2020
        }
        for (AbstractDAO dao : daos) {
            dao.set(connection);
            log.debug(dao.toString());
            log.debug(connection.toString());
        }
        log.debug("TRANSACTION BEGIN");
    }

    public void end() {
        try {
            connection.setAutoCommit(true);
            log.debug("TRANSACTION END");
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
            log.debug("TRANSACTION COMMIT");
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

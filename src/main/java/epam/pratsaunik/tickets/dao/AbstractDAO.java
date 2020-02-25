package epam.pratsaunik.tickets.dao;

import epam.pratsaunik.tickets.entity.Entity;
import epam.pratsaunik.tickets.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> {
    protected Connection connection;

    public abstract int getNumberOfRecords() throws DaoException;

    public abstract long create(T entity) throws DaoException;

    public abstract T update(T entity) throws DaoException;

    public abstract boolean delete(long id) throws DaoException;

    public abstract List<T> findById(long id) throws DaoException;

    public abstract List<T> findAll() throws DaoException;

    public abstract List<T> findRange(int start, int recordsPerPage) throws DaoException;

    public void close(Statement statement) throws DaoException {
        if (statement != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    void set(Connection connection) {
        this.connection = connection;
    }
}

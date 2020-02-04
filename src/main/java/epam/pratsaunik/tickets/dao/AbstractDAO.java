package epam.pratsaunik.tickets.dao;


import epam.pratsaunik.tickets.entity.Entity;
import epam.pratsaunik.tickets.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO <K,T extends Entity>{
    protected Connection connection;

    public abstract List<T> findAll() throws DaoException;
    public abstract boolean delete (K id);
    public abstract boolean delete (T entity);
    public abstract T create (T entity) throws DaoException;
    public abstract T update (T entity);

    public void close(Statement statement)  {
        if (statement!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void set(Connection connection){this.connection=connection;
    }

}

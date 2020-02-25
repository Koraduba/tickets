package epam.pratsaunik.tickets.dao;


import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.DaoException;

import java.util.List;

public abstract class UserDao<T extends User> extends AbstractDAO {

    public abstract List<T> findUserByName(String name) throws DaoException;
    public abstract List<T> findUsersByEvent(Event event) throws DaoException;
    public abstract List<T> findUserByLogin(String login) throws DaoException;

}

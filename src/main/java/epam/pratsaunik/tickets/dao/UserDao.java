package epam.pratsaunik.tickets.dao;


import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.DaoException;

import java.util.List;

public abstract class UserDao extends AbstractDAO {

    public abstract List<User> findUserByName(String name) throws DaoException;
    public abstract List<User> findUserByEvent(String name);
    public abstract List<User>  findUserByLogin(String login) throws DaoException;
}

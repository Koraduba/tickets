package epam.pratsaunik.tickets.service.impl;


import epam.pratsaunik.tickets.dao.AbstractDAO;
import epam.pratsaunik.tickets.dao.EntityTransaction;
import epam.pratsaunik.tickets.dao.UserDao;
import epam.pratsaunik.tickets.dao.impl.UserDaoImpl;
import epam.pratsaunik.tickets.entity.Entity;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.DaoException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.hash.PasswordHash;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService, Service {
    private final static Logger log = LogManager.getLogger();

    @Override
    public Integer getNumberOfRecords() throws ServiceLevelException {
        Integer number = null;
        UserDao userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            number = userDao.getNumberOfRecords();
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }

        return number;
    }

    @Override
    public long create(User user) throws ServiceLevelException {
        long id=0;
        UserDaoImpl userDao = new UserDaoImpl();
        String passwordEncoded = PasswordHash.getHash(user.getPassword());
        user.setPassword(passwordEncoded);
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.begin(userDao);
            id=userDao.create(user);
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return id;
    }

    @Override
    public boolean update(User user) throws ServiceLevelException {
        EntityTransaction entityTransaction = new EntityTransaction();
        UserDaoImpl userDao = new UserDaoImpl();
        String passwordEncoded = PasswordHash.getHash(user.getPassword());
        user.setPassword(passwordEncoded);
        entityTransaction.begin(userDao);
        try {
            userDao.update(user);
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        }finally {
            entityTransaction.end();
        }
        return true;
    }

    @Override
    public List<User> findRange(int currentPage, int recordsPerPage) throws ServiceLevelException {
        List<User> users;
        int start = currentPage * recordsPerPage - recordsPerPage;
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            users = userDao.findRange(start, recordsPerPage);
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return users;
    }

    @Override
    public List<User> findAllUsers() throws ServiceLevelException {
        List<User> users;
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            users = userDao.findAll();
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return users;
    }

    @Override
    public User createAdmin() throws ServiceLevelException {
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        User admin = null;
        try {
            List<User> admins = userDao.findUserByLogin("Admin");
            if (admins.isEmpty()) {
                admin = new User();
                admin.setName("Admin");
                admin.setSurname("Admin");
                admin.setLogin("Admin");
                admin.setRole(Role.ADMINISTRATOR);
                admin.setPassword("1111");
                admin.setEmail("admin@admin");
                create(admin);
            } else {
                admin = admins.get(0);
            }
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return admin;
    }

    @Override
    public boolean delete(User user) throws ServiceLevelException {
        UserDao userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            userDao.delete(user.getUserId());
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return true;
    }


    public List<User> findUserByLogin(String login) throws ServiceLevelException {
        UserDaoImpl userDao = new UserDaoImpl();
        List<User> user;
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            user = userDao.findUserByLogin(login);
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return user;
    }

    public boolean checkUser(String login, String password, User user) {
        log.debug(user.getPassword());
        log.debug(PasswordHash.getHash(password));
        log.debug(login);
        log.debug(user.getLogin());
        return login.equalsIgnoreCase(user.getLogin()) && PasswordHash.getHash(password).equals(user.getPassword());
    }


}

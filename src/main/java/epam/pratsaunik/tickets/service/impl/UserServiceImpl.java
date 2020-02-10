package epam.pratsaunik.tickets.service.impl;


import epam.pratsaunik.tickets.dao.EntityTransaction;
import epam.pratsaunik.tickets.dao.UserDao;
import epam.pratsaunik.tickets.dao.impl.UserDaoImpl;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.DaoException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.hash.PasswordHash;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.graalvm.compiler.lir.LIRInstruction;

import java.util.List;

public class UserServiceImpl implements UserService, Service {

    private final static Logger log = LogManager.getLogger();

    @Override
    public Integer getNumberOfRecords() {
        Integer number=null;
        UserDao userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            number=userDao.getNumberOfRecords();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        entityTransaction.commit();
        return number;
    }

    @Override
    public User create(User user) throws ServiceLevelException {

        UserDao userDao = new UserDaoImpl();
        String passwordEncoded=PasswordHash.getHash(user.getPassword());
        user.setPassword(passwordEncoded);
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            userDao.create(user);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return user;
    }

    @Override
    public List<User> findRange(int currentPage, int recordsPerPage) throws ServiceLevelException {
        List<User> users;
        int start = currentPage * recordsPerPage - recordsPerPage;
        UserDao userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            users = userDao.findRange(start,recordsPerPage);
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }
        entityTransaction.commit();
        entityTransaction.end();
        return users;
    }

    @Override
    public List<User> findAllUsers() throws ServiceLevelException {
        List<User> users;
        UserDao userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }
        entityTransaction.commit();
        entityTransaction.end();
        return users;
    }

    @Override
    public User createAdmin() throws ServiceLevelException {
        UserDao userDao = new UserDaoImpl();
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

        } catch (DaoException e) {
            e.printStackTrace();
        } finally {
            entityTransaction.end();
        }
        return admin;
    }

    @Override
    public boolean deleteAdmin() {
        return false;
    }

    public List<User> findUserByLogin(String login) throws ServiceLevelException {
        UserDao userDao = new UserDaoImpl();
        List<User> user;
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(userDao);
        try {
            user = userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }
        entityTransaction.commit();
        entityTransaction.end();
        return user;
    }

    public boolean checkUser(String login, String password, User user) {
        log.debug(user.getPassword());
        log.debug(PasswordHash.getHash(password));
        log.debug(login);
        log.debug(user.getLogin());
        return login.equalsIgnoreCase(user.getLogin()) && PasswordHash.getHash(password).equals(user.getPassword());
    }

    ;


}

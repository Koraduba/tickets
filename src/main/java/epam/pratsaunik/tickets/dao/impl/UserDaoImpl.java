package epam.pratsaunik.tickets.dao.impl;


import epam.pratsaunik.tickets.dao.ColumnName;
import epam.pratsaunik.tickets.dao.UserDao;
import epam.pratsaunik.tickets.entity.Entity;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends UserDao {
    private final static Logger log = LogManager.getLogger();
    private final static String SQL_SELECT_USER_BY_NAME = "SELECT user_id, user.name, surname, email, login, " +
            "password, role.name " +
            "FROM user,role WHERE name=? AND user.role=role_id";
    private final static String SQL_CREATE_USER = "INSERT INTO user(name,surname,email,role, login, password) " +
            "VALUES(?,?,?,?,?,?)";
    private final static String SQL_SELECT_USER_BY_LOGIN = "SELECT user_id, user.name, surname, email, login, " +
            "password, role.name " +
            "FROM user,role WHERE login=? AND user.role=role_id";
    private final static String SQL_FIND_ALL = "SELECT user_id, user.name, surname, email, login, " +
            "password, role.name " +
            "FROM user,role WHERE user.role=role_id";
    private final static String SQL_FIND_RANGE = "SELECT user_id, user.name, surname, email, login, " +
            "password, role.name " +
            "FROM user,role WHERE user.role=role_id LIMIT ?,?";
    private final static String SQL_GET_NUMBER_OF_RECORDS = "SELECT count(user_id) FROM user";


    @Override
    public List<User> findUserByName(String name) throws DaoException {

        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.USER_ID));
                user.setEmail(resultSet.getString(ColumnName.USER_EMAIL));
                user.setName(resultSet.getString(ColumnName.USER_NAME));
                user.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                user.setRole(Role.valueOf(resultSet.getString(ColumnName.USER_ROLE)));
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public List<User> findUserByEvent(String name) {
        return null;
    }

    @Override
    public List<User> findUserByLogin(String login) throws DaoException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            log.debug(login);
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            log.debug(login);
            ResultSet resultSet = statement.executeQuery();
            log.debug(resultSet);
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.USER_ID));
                user.setEmail(resultSet.getString(ColumnName.USER_EMAIL));
                user.setName(resultSet.getString(ColumnName.USER_NAME));
                user.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                user.setRole(Role.valueOf(resultSet.getString(ColumnName.USER_ROLE)));
                user.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                user.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
            while (resultSet.next()) {
                System.out.println("!!!");
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.USER_ID));
                user.setEmail(resultSet.getString(ColumnName.USER_EMAIL));
                user.setName(resultSet.getString(ColumnName.USER_NAME));
                user.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                user.setRole(Role.valueOf(resultSet.getString(ColumnName.USER_ROLE)));
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException();
            }
        }
        return users;
    }

    @Override
    public List findRange(int start, int recordsPerPage) throws DaoException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_RANGE);
            statement.setInt(1, start);
            statement.setInt(2,recordsPerPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.USER_ID));
                user.setEmail(resultSet.getString(ColumnName.USER_EMAIL));
                user.setName(resultSet.getString(ColumnName.USER_NAME));
                user.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                user.setRole(Role.valueOf(resultSet.getString(ColumnName.USER_ROLE)));
                user.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                user.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public int getNumberOfRecords() throws DaoException {
        int result=0;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_NUMBER_OF_RECORDS);
            while (resultSet.next()) {
                result = resultSet.getInt(1);
                log.debug("NrOfRows: "+result);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException();
            }
        }
        return result;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }

    @Override
    public long create(Entity entity) throws DaoException {
        PreparedStatement statement = null;
        long id=0;
        try {
            statement = connection.prepareStatement(SQL_CREATE_USER, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(3, ((User) entity).getEmail());
            statement.setString(1, ((User) entity).getName());
            statement.setString(2, ((User) entity).getSurname());
            log.debug(((User) entity).getRole().ordinal());
            statement.setInt(4, ((User) entity).getRole().ordinal() + 1);//TODO
            statement.setString(5, (((User) entity).getLogin()));
            statement.setString(6, (((User) entity).getPassword()));
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id=resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

        return id;
    }

    @Override
    public Entity update(Entity entity) {
        return null;
    }
}

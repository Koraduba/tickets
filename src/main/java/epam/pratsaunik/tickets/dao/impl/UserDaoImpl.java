package epam.pratsaunik.tickets.dao.impl;


import epam.pratsaunik.tickets.dao.ColumnName;
import epam.pratsaunik.tickets.dao.UserDao;
import epam.pratsaunik.tickets.entity.*;
import epam.pratsaunik.tickets.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl extends UserDao {
    private final static Logger log = LogManager.getLogger();
    private final static String SQL_GET_NUMBER_OF_RECORDS = "SELECT count(user_id) FROM user";
    private final static String SQL_CREATE_USER = "INSERT INTO user(name,surname,email,role, login, password) " +
            "VALUES(?,?,?,?,?,?)";
    private final static String SQL_UPDATE_USER = "UPDATE user SET name=?,surname=?,email=?,role=?,login=?,password=? WHERE user_id=?";
    private final static String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE user_id=?";
    private final static String SQL_FIND_USER_BY_ID = "SELECT user_id, user.name, surname, email, login, " +
            "password, role.name FROM user,role WHERE user_id=? AND user.role=role_id";
    private final static String SQL_FIND_ALL_USERS = "SELECT user_id, user.name, surname, email, login, " +
            "password, role.name FROM user,role WHERE user.role=role_id";
    private final static String SQL_FIND_RANGE_OF_USERS = "SELECT user_id, user.name, surname, email, login, " +
            "password, role.name FROM user,role WHERE user.role=role_id LIMIT ?,?";

    private final static String SQL_FIND_USER_BY_NAME = "SELECT user_id, user.name, surname, email, login, " +
            "password, role.name " +
            "FROM user,role WHERE name=? AND user.role=role_id";
    private final static String SQL_FIND_USERS_BY_EVENT = "SELECT user_id, user.name,surname,email, login," +
            "password,role.name FROM user INNER JOIN role ON order.role=role.role_id " +
            "INNER JOIN order ON user_id=order.user " +
            "INNER JOIN order_line ON order.order_id=order_line.order " +
            "INNER JOIN ticket ON order_line.ticket=ticket.ticket_id " +
            "INNER JOIN event ON ticket.event=event.event_id WHERE event_id=?";
    private final static String SQL_FIND_USER_BY_LOGIN = "SELECT user_id, user.name, surname, email, login, " +
            "password, role.name " +
            "FROM user,role WHERE login=? AND user.role=role_id";


    @Override
    public int getNumberOfRecords() throws DaoException {
        int result = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_NUMBER_OF_RECORDS);
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return result;
    }

    @Override
    public long create(Entity entity) throws DaoException {
        User user = (User) entity;
        PreparedStatement statement = null;
        ResultSet resultSet=null;
        long id = 0;
        try {
            statement = connection.prepareStatement(SQL_CREATE_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(3, user.getEmail());
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setInt(4, user.getRole().ordinal() + 1);
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return id;
    }

    @Override
    public User update(Entity entity) throws DaoException {
        User user = (User) entity;
        User oldUser = findById(user.getUserId()).get(0);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(3, user.getEmail());
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setInt(4, user.getRole().ordinal() + 1);
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            statement.setLong(7, user.getUserId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return oldUser;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.info("User cannot be deleted", e);
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return true;
    }


    @Override
    public List<User> findById(long id) throws DaoException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet=null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.USER_ID));
                user.setEmail(resultSet.getString(ColumnName.USER_EMAIL));
                user.setName(resultSet.getString(ColumnName.USER_NAME));
                user.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                user.setRole(Role.valueOf(resultSet.getString(ColumnName.USER_ROLE)));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return users;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet=null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.USER_ID));
                user.setEmail(resultSet.getString(ColumnName.USER_EMAIL));
                user.setName(resultSet.getString(ColumnName.USER_NAME));
                user.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                user.setRole(Role.valueOf(resultSet.getString(ColumnName.USER_ROLE)));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return users;
    }

    @Override
    public List<User> findRange(int start, int recordsPerPage) throws DaoException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet=null;
        try {
            statement = connection.prepareStatement(SQL_FIND_RANGE_OF_USERS);
            statement.setInt(1, start);
            statement.setInt(2, recordsPerPage);
            resultSet = statement.executeQuery();
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
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return users;
    }

    @Override
    public List<User> findUserByName(String name) throws DaoException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet=null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.USER_ID));
                user.setEmail(resultSet.getString(ColumnName.USER_EMAIL));
                user.setName(resultSet.getString(ColumnName.USER_NAME));
                user.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                user.setRole(Role.valueOf(resultSet.getString(ColumnName.USER_ROLE)));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return users;
    }

    @Override
    public List<User> findUsersByEvent(Event event) throws DaoException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Long eventId = event.getEventId();
        try {
            statement = connection.prepareStatement(SQL_FIND_USERS_BY_EVENT);
            statement.setLong(1, eventId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.USER_ID));
                user.setEmail(resultSet.getString(ColumnName.USER_EMAIL));
                user.setName(resultSet.getString(ColumnName.USER_NAME));
                user.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                user.setRole(Role.valueOf(resultSet.getString(ColumnName.USER_ROLE)));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return users;
    }

    @Override
    public List<User> findUserByLogin(String login) throws DaoException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            log.debug(login);
            statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            statement.setString(1, login);
            log.debug(login);
            resultSet = statement.executeQuery();
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
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return users;
    }
}

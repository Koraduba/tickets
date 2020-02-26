package epam.pratsaunik.tickets.dao.impl;

import epam.pratsaunik.tickets.dao.ColumnName;
import epam.pratsaunik.tickets.dao.OrderDao;
import epam.pratsaunik.tickets.entity.*;
import epam.pratsaunik.tickets.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl extends OrderDao {
    private final static Logger log = LogManager.getLogger();

    private final static String SQL_FIND_ORDERS_BY_EVENT = "SELECT order_id, user, date FROM `order` " +
            "INNER JOIN order_line ON order.order_id=order_line.order " +
            "INNER JOIN ticket ON order_line.ticket=ticket.ticket_id";
    private final static String SQL_FIND_ORDERS_ABOVE_AMOUNT = "SELECT order_id,user, date, sum FROM " +
            "(SELECT order_id,user,date, SUM(ticket.price*order_line.quantity) AS sum FROM `order` " +
            "INNER JOIN order_line ON order.order_id=order_line.order " +
            "INNER JOIN ticket ON order_line.ticket=ticket.ticket_id GROUP BY order_id) AS sums " +
            "WHERE sum>=?";
    private final static String SQL_FIND_ORDERS_BELOW_AMOUNT = "SELECT order_id,user, date, sum FROM " +
            "(SELECT order_id,user,date, SUM(ticket.price*order_line.quantity) AS sum FROM `order` " +
            "INNER JOIN order_line ON order.order_id=order_line.order " +
            "INNER JOIN ticket ON order_line.ticket=ticket.ticket_id GROUP BY order_id) AS sums " +
            "WHERE sum<?";
    private final static String SQL_FIND_ORDERS_BY_USER = "SELECT order_id, user, date FROM `order` WHERE user=?";
    private final static String SQL_CREATE_ORDER_LINE = "INSERT INTO order_line(ticket,quantity,`order`) VALUES(?,?,?)";
    private final static String SQL_UPDATE_ORDER_LINE = "UPDATE order_line SET ticket=?,quantity=?,`order`=? " +
            "WHERE order_line_id=?";
    private final static String SQL_DELETE_ORDER_LINE = "DELETE FROM order_line WHERE order_line_id=?";
    private final static String SQL_FIND_ORDER_LINE_BY_ID = "SELECT order_line_id,ticket,quantity,`order` FROM order_line " +
            "WHERE order_line_id=?";
    private final static String SQL_FIND_ORDER_LINES_BY_ORDER = "SELECT order_line_id,ticket,quantity,`order` FROM order_line " +
            "WHERE `order`=?";

    private final static String SQL_GET_NUMBER_OF_RECORDS = "SELECT count(order_id) FROM `order`";
    private final static String SQL_CREATE_ORDER = "INSERT INTO `order`(user,date) VALUES(?,?)";
    private final static String SQL_UPDATE_ORDER = "UPDATE `order` SET user=?,date=? WHERE order_id=?";
    private final static String SQL_DELETE_ORDER = "DELETE FROM `order` WHERE order_id=?";
    private final static String SQL_FIND_ORDERS_BY_ID = "SELECT order_id, user, date FROM `order` WHERE order_id=?";
    private final static String SQL_FIND_ALL_ORDERS = "SELECT order_id, user, date FROM `order`";
    private final static String SQL_FIND_RANGE_OF_ORDERS = "SELECT order_id, user, date From `order` LIMIT ?,?";


    @Override
    public List<Order> findOrdersByEvent(Event event) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDERS_BY_EVENT);
            statement.setLong(1, event.getEventId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.ORDER_USER));
                order.setUser(user);
                Date date = new Date(resultSet.getLong(ColumnName.ORDER_DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
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
        return orderList;
    }

    @Override
    public List<Order> findOrdersAboveAmount(BigDecimal amount) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDERS_ABOVE_AMOUNT);
            statement.setBigDecimal(1, amount);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.ORDER_USER));
                order.setUser(user);
                order.setUser(user);
                Date date = new Date(resultSet.getLong(ColumnName.ORDER_DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
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
        return orderList;

    }

    @Override
    public List<Order> findOrdersBelowAmount(BigDecimal amount) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDERS_BELOW_AMOUNT);
            statement.setBigDecimal(1, amount);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.ORDER_USER));
                order.setUser(user);
                order.setUser(user);
                Date date = new Date(resultSet.getLong(ColumnName.ORDER_DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
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
        return orderList;

    }

    @Override
    public List<Order> findOrdersByUser(User user) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDERS_BY_USER);
            log.debug("user: "+user);
            log.debug("statement: "+statement);
            statement.setLong(1, user.getUserId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                order.setUser(user);
                Date date = new Date(resultSet.getLong(ColumnName.ORDER_DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
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
        return orderList;

    }

    @Override
    public long createOrderLine(OrderLine orderline) throws DaoException {
        long id = 0;
        log.debug("OrderDaoImpl::createOrderLine");
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_ORDER_LINE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, orderline.getTicket().getTicketId());
            statement.setInt(2, orderline.getTicketQuantity());
            statement.setLong(3, orderline.getOrder().getOrderId());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong(1);
            orderline.setOrderLineId(id);
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
    public OrderLine updateOrderLine(OrderLine orderLine) throws DaoException {
        OrderLine oldOrderLine = findOrderLineById(orderLine.getOrderLineId()).get(0);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ORDER_LINE);
            statement.setLong(1, orderLine.getTicket().getTicketId());
            statement.setInt(2, orderLine.getTicketQuantity());
            statement.setLong(3, orderLine.getOrder().getOrderId());
            statement.setLong(4, orderLine.getOrderLineId());
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
        return oldOrderLine;
    }

    @Override
    public boolean deleteOrderLine(OrderLine orderLine) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ORDER_LINE);
            statement.setLong(1, orderLine.getOrderLineId());
            statement.execute();
        } catch (SQLException e) {
            log.info("Order line cannot be deleted", e);
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
    public List<OrderLine> findOrderLineById(long id) throws DaoException {
        List<OrderLine> orderLineList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDER_LINE_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrderLineId(resultSet.getLong(ColumnName.ORDER_LINE_ID));
                orderLine.setTicketQuantity(resultSet.getInt(ColumnName.ORDER_LINE_QUANTITY));
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getLong(ColumnName.ORDER_LINE_TICKET));
                orderLine.setTicket(ticket);
                Order order = findById(resultSet.getLong(ColumnName.ORDER_LINE_ORDER)).get(0);
                orderLine.setTicket(ticket);
                orderLine.setOrder(order);
                orderLineList.add(orderLine);
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
        return orderLineList;
    }

    @Override
    public List<OrderLine> findOrderLinesByOrder(Order order) throws DaoException {
        List<OrderLine> orderLineList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDER_LINES_BY_ORDER);
            statement.setLong(1, order.getOrderId());
            log.debug(statement);
            resultSet = statement.executeQuery();
            log.debug("OrderDaoImpl::findOrderLinesByOrder resultset" + resultSet);
            while (resultSet.next()) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrderLineId(resultSet.getLong(ColumnName.ORDER_LINE_ID));
                orderLine.setTicketQuantity(resultSet.getInt(ColumnName.ORDER_LINE_QUANTITY));
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getLong(ColumnName.ORDER_LINE_TICKET));
                orderLine.setTicket(ticket);
                orderLine.setOrder(order);
                orderLineList.add(orderLine);
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
        return orderLineList;
    }

    @Override
    public int getNumberOfRecords() throws DaoException {
        int result = 0;
        log.debug("OrderDaoImpl::getNumberOfRecords");
        log.debug("Connection:" + connection);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            log.debug("Statement:" + statement);
            resultSet = statement.executeQuery(SQL_GET_NUMBER_OF_RECORDS);
            log.debug("resultSet:" + resultSet);
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
        long id = 0;
        log.debug("OrderDaoImpl::create");
        Order order = (Order) entity;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
            Long userId = order.getUser().getUserId();
            statement.setLong(1, userId);
            log.debug("userId" + userId);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date;
            try {
                date = dateFormat.parse(order.getDate());
            } catch (ParseException e) {
                throw new DaoException(e);
            }
            statement.setLong(2, date.getTime());
            log.debug(statement);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong(1);
        } catch (SQLException e) {
            log.debug(e);
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
    public Order update(Entity entity) throws DaoException {
        Order order = (Order) entity;
        Order oldOrder = findById(order.getOrderId()).get(0);
        long id = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ORDER);
            statement.setLong(1, order.getUser().getUserId());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            try {
                date = dateFormat.parse(order.getDate());
            } catch (ParseException e) {
                throw new DaoException(e);
            }
            statement.setLong(2, (date.getTime()));
            statement.setLong(3, order.getOrderId());
            statement.executeUpdate();
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
        return oldOrder;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ORDER);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.info("Order cannot be deleted", e);
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
    public List<Order> findById(long id) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDERS_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.ORDER_USER));
                order.setUser(user);
                order.setUser(user);
                Date date = new Date(resultSet.getLong(ColumnName.ORDER_DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
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
        return orderList;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_ALL_ORDERS);
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.ORDER_USER));
                order.setUser(user);
                order.setUser(user);
                Date date = new Date(resultSet.getLong(ColumnName.ORDER_DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
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
        return orderList;
    }

    @Override
    public List<Order> findRange(int start, int recordsPerPage) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_RANGE_OF_ORDERS);
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = new User();
                user.setUserId(resultSet.getLong(ColumnName.ORDER_USER));
                order.setUser(user);
                order.setUser(user);
                Date date = new Date(resultSet.getLong(ColumnName.ORDER_DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
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
        return orderList;
    }
}

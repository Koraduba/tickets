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
import java.util.SplittableRandom;

public class OrderDaoImpl extends OrderDao {
    private final static Logger log = LogManager.getLogger();
    private UserDaoImpl userDao = new UserDaoImpl();
    private EventDaoImpl eventDao = new EventDaoImpl();

    private final static String SQL_FIND_ORDERS_BY_EVENT = "SELECT order_id, user, date FROM order " +
            "INNER JOIN order_line ON order.order_id=order_line.order " +
            "INNER JOIN ticket ON order_line.ticket=ticket.ticket_id";
    private final static String SQL_FIND_ORDERS_ABOVE_AMOUNT = "SELECT order_id,user, date, sum FROM " +
            "(SELECT order_id,user,date, SUM(ticket.price*order_line.quantity) AS sum FROM order " +
            "INNER JOIN order_line ON order.order_id=order_line.order " +
            "INNER JOIN ticket ON order_line.ticket=ticket.ticket_id GROUP BY order_id) AS sums " +
            "WHERE sum>=?";
    private final static String SQL_FIND_ORDERS_BELOW_AMOUNT = "SELECT order_id,user, date, sum FROM " +
            "(SELECT order_id,user,date, SUM(ticket.price*order_line.quantity) AS sum FROM order " +
            "INNER JOIN order_line ON order.order_id=order_line.order " +
            "INNER JOIN ticket ON order_line.ticket=ticket.ticket_id GROUP BY order_id) AS sums " +
            "WHERE sum<?";
    private final static String SQL_FIND_ORDERS_BY_USER = "SELECT order_id, user, date FROM order WHERE user=?";
    private final static String SQL_CREATE_ORDER_LINE = "INSERT INTO order_line(ticket,quantity,order) VALUES(?,?,?)";
    private final static String SQL_UPDATE_ORDER_LINE = "UPDATE order_line SET ticket=?,quantity=?,order=? " +
            "WHERE order_line_id=?";
    private final static String SQL_DELETE_ORDER_LINE = "DELETE FROM order_line WHERE order_line_id=?";
    private final static String SQL_FIND_ORDER_LINE_BY_ID = "SELECT order_line_id,ticket,quantity,order FROM order_line" +
            "WHERE order_line_id=?";
    private final static String SQL_FIND_ORDER_LINES_BY_ORDER = "SELECT order_line_id,ticket,quantity,order FROM order_line" +
            "WHERE order=?";

    private final static String SQL_GET_NUMBER_OF_RECORDS = "SELECT count(order_id) FROM order";
    private final static String SQL_CREATE_ORDER = "INSERT INTO order(user,date) " +
            "VALUES(?,?)";
    private final static String SQL_UPDATE_ORDER = "UPDATE order SET user=?,date=? WHERE order_id=?";
    private final static String SQL_DELETE_ORDER = "DELETE FROM order WHERE order_id=?";

    private final static String SQL_FIND_ORDERS_BY_ID = "SELECT order_id, user, date FROM order WHERE order_id=?";
    private final static String SQL_FIND_ALL_ORDERS = "SELECT order_id, user, date FROM order";
    private final static String SQL_FIND_RANGE_OF_ORDERS = "SELECT order_id, user, date From order LIMIT ?,?";


    @Override
    public List<Order> findOrdersByEvent(Event event) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDERS_BY_EVENT);
            statement.setLong(1, event.getEventId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = userDao.findById(resultSet.getLong(ColumnName.ORDER_USER)).get(0);
                order.setUser(user);
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return orderList;
    }

    @Override
    public List<Order> findOrdersAboveAmount(BigDecimal amount) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDERS_ABOVE_AMOUNT);
            statement.setBigDecimal(1, amount);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = userDao.findById(resultSet.getLong(ColumnName.ORDER_USER)).get(0);
                order.setUser(user);
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return orderList;

    }

    @Override
    public List<Order> findOrdersBelowAmount(BigDecimal amount) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDERS_BELOW_AMOUNT);
            statement.setBigDecimal(1, amount);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = userDao.findById(resultSet.getLong(ColumnName.ORDER_USER)).get(0);
                order.setUser(user);
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return orderList;

    }

    @Override
    public List<Order> findOrdersByUser(User user) throws DaoException {
            List<Order> orderList = new ArrayList<>();
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(SQL_FIND_ORDERS_BY_USER);
                statement.setLong(1, user.getUserId());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                    order.setUser(user);
                    Date date = new Date(resultSet.getLong("date"));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    order.setDate(simpleDateFormat.format(date));
                    orderList.add(order);
                }
                resultSet.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
            return orderList;

    }

    @Override
    public long createOrderLine(OrderLine orderline) throws DaoException {
        long id=0;
        PreparedStatement statement=null;
        try {
            statement=connection.prepareStatement(SQL_CREATE_ORDER_LINE,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1,orderline.getTicket().getTicketId());
            statement.setInt(2, orderline.getTicketQuantity());
            statement.setLong(3,orderline.getOrder().getOrderId());
            statement.execute();
            ResultSet resultSet=statement.getGeneratedKeys();
            resultSet.next();
            id=resultSet.getLong(1);
            orderline.setOrderLineId(id);
            resultSet.close();
        } catch (SQLException e) {
           throw new DaoException(e);
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return id;
    }

    @Override
    public OrderLine updateOrderLine(OrderLine orderLine) throws DaoException {
        OrderLine oldOrderLine=findOrderLineById(orderLine.getOrderLineId()).get(0);
        PreparedStatement statement=null;
        try {
            statement=connection.prepareStatement(SQL_UPDATE_ORDER_LINE);
            statement.setLong(1,orderLine.getTicket().getTicketId());
            statement.setInt(2, orderLine.getTicketQuantity());
            statement.setLong(3,orderLine.getOrder().getOrderId());
            statement.setLong(4,orderLine.getOrderLineId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return oldOrderLine;
    }

    @Override
    public boolean deleteOrderLine(OrderLine orderLine) throws DaoException {
        PreparedStatement statement=null;
        try {
            statement=connection.prepareStatement(SQL_DELETE_ORDER_LINE);
            statement.setLong(1,orderLine.getOrderLineId());
            statement.execute();
        } catch (SQLException e) {
            log.info("Order line cannot be deleted",e);
            return false;
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return true;
    }

    @Override
    public List<OrderLine> findOrderLineById(long id) throws DaoException {
        List<OrderLine> orderLineList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDER_LINE_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrderLineId(resultSet.getLong(ColumnName.ORDER_LINE_ID));
                orderLine.setTicketQuantity(resultSet.getInt(ColumnName.ORDER_LINE_QUANTITY));
                Ticket ticket = eventDao.findTicketById(resultSet.getLong(ColumnName.ORDER_LINE_TICKET)).get(0);
                Order order = findById(resultSet.getLong(ColumnName.ORDER_LINE_ORDER)).get(0);
                orderLine.setTicket(ticket);
                orderLine.setOrder(order);
                orderLineList.add(orderLine);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return orderLineList;
    }

    @Override
    public List<OrderLine> findOrderLinesByOrder(Order order) throws DaoException {
        List<OrderLine> orderLineList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDER_LINES_BY_ORDER);
            statement.setLong(1, order.getOrderId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrderLineId(resultSet.getLong(ColumnName.ORDER_LINE_ID));
                orderLine.setTicketQuantity(resultSet.getInt(ColumnName.ORDER_LINE_QUANTITY));
                Ticket ticket = eventDao.findTicketById(resultSet.getLong(ColumnName.ORDER_LINE_TICKET)).get(0);
                orderLine.setTicket(ticket);
                orderLine.setOrder(order);
                orderLineList.add(orderLine);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return orderLineList;
    }

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
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                throw new DaoException();
            }
        }
        return result;
    }

    @Override
    public long create(Entity entity) throws DaoException {
        long id=0;
        Order order = (Order)entity;
        PreparedStatement statement=null;
        try {
            statement=connection.prepareStatement(SQL_CREATE_ORDER,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1,order.getUser().getUserId());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            try {
                date = dateFormat.parse(order.getDate());
            } catch (ParseException e) {
                throw new DaoException(e);
            }
            statement.setLong(2, (date.getTime()));
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return id;
    }

    @Override
    public Order update(Entity entity) throws DaoException {
        Order order=(Order)entity;
        Order oldOrder=findById(order.getOrderId()).get(0);
        long id=0;
        PreparedStatement statement=null;
        try {
            statement=connection.prepareStatement(SQL_UPDATE_ORDER);
            statement.setLong(1,order.getUser().getUserId());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            try {
                date = dateFormat.parse(order.getDate());
            } catch (ParseException e) {
                throw new DaoException(e);
            }
            statement.setLong(2, (date.getTime()));
            statement.setLong(3,order.getOrderId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return oldOrder;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        PreparedStatement statement=null;
        try {
            statement=connection.prepareStatement(SQL_DELETE_ORDER);
            statement.setLong(1,id);
            statement.execute();
        } catch (SQLException e) {
            log.info("Order cannot be deleted",e);
            return false;
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return true;
    }

    @Override
    public List<Order> findById(long id) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ORDERS_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = userDao.findById(resultSet.getLong(ColumnName.ORDER_USER)).get(0);
                order.setUser(user);
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return orderList;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDERS);
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = userDao.findById(resultSet.getLong(ColumnName.ORDER_USER)).get(0);
                order.setUser(user);
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return orderList;
    }

    @Override
    public List<Order> findRange(int start, int recordsPerPage) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_RANGE_OF_ORDERS);
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(ColumnName.ORDER_ID));
                User user = userDao.findById(resultSet.getLong(ColumnName.ORDER_USER)).get(0);
                order.setUser(user);
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setDate(simpleDateFormat.format(date));
                orderList.add(order);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return orderList;
    }
}

package epam.pratsaunik.tickets.dao;

import epam.pratsaunik.tickets.dao.AbstractDAO;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Order;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public abstract class OrderDao extends AbstractDAO {

    public abstract List<Order> findOrdersByEvent (Event event) throws DaoException;
    public abstract List<Order> findOrdersAboveAmount (BigDecimal amount) throws DaoException;
    public abstract List<Order> findOrdersBelowAmount (BigDecimal amount) throws DaoException;
    public abstract List<Order> findOrdersByUser (User user) throws DaoException;
    public abstract long createOrderLine(OrderLine orderline) throws DaoException;
    public abstract OrderLine updateOrderLine(OrderLine orderLine) throws DaoException;
    public abstract boolean deleteOrderLine(OrderLine orderLine) throws DaoException;
    public abstract List<OrderLine>findOrderLineById(long id) throws DaoException;
    public abstract List<OrderLine> findOrderLinesByOrder(Order order) throws DaoException;
}

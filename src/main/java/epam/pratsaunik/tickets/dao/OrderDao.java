package epam.pratsaunik.tickets.dao;

import epam.pratsaunik.tickets.dao.AbstractDAO;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Order;
import epam.pratsaunik.tickets.entity.User;

import java.util.List;

public abstract class OrderDao extends AbstractDAO {

    public abstract List<Order> findOrdersByEvent (Event event);
    public abstract List<Order> findOrdersAboveAmount (long amount);
    public abstract List<Order> findOrdersBelowAmount (long amount);
    public abstract List<Order> findOrdersByUser (User user);

}

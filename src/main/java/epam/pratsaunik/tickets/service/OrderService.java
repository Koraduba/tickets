package epam.pratsaunik.tickets.service;

import epam.pratsaunik.tickets.entity.*;
import epam.pratsaunik.tickets.exception.ServiceLevelException;

import java.util.List;

public interface OrderService {

    public abstract List<Order> findOrdersByUser (User user);
    public abstract long createOrderLine(OrderLine orderLine) throws ServiceLevelException;
    public abstract OrderLine update (OrderLine orderLine);
    public abstract boolean deleteOrderLine (OrderLine orderLine);
    public abstract List<OrderLine> findOrderLinesByOrder (Order order);

    public abstract long create(Order order) throws ServiceLevelException;
    public abstract Order update(Order order);
    public abstract boolean delete(Order order);
    public abstract List<Order> findAll();
    public abstract List<Order> findRange(int start, int perPage);
    public abstract Order findById(long id) throws ServiceLevelException;
}

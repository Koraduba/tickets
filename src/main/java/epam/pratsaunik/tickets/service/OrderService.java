package epam.pratsaunik.tickets.service;

import epam.pratsaunik.tickets.entity.*;
import epam.pratsaunik.tickets.exception.ServiceLevelException;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    List<Order> findOrdersByUser (User user) throws ServiceLevelException;
    List<Order> findOrdersBelowAmount(BigDecimal amount) throws ServiceLevelException;
    List<Order> findOrdersAboveAmount(BigDecimal amount) throws ServiceLevelException;
    long createOrderLine(OrderLine orderLine) throws ServiceLevelException;
    OrderLine update (OrderLine orderLine);
    boolean deleteOrderLine (OrderLine orderLine);
    List<OrderLine> findOrderLinesByOrder (Order order) throws ServiceLevelException;

    long create(Order order) throws ServiceLevelException;
    Order update(Order order);
    boolean delete(Order order);
    List<Order> findAll();
    List<Order> findRange(int start, int perPage);
    Order findById(long id) throws ServiceLevelException;
}

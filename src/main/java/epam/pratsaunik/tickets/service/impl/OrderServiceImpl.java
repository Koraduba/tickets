package epam.pratsaunik.tickets.service.impl;

import epam.pratsaunik.tickets.entity.Order;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.OrderService;
import epam.pratsaunik.tickets.service.Service;

import java.util.List;

public class OrderServiceImpl implements OrderService,Service {

    @Override
    public List<Order> findOrdersByUser(User user) {
        return null;
    }

    @Override
    public long createOrderLine(OrderLine orderLine) {
        return 0;
    }

    @Override
    public OrderLine update(OrderLine orderLine) {
        return null;
    }

    @Override
    public boolean deleteOrderLine(OrderLine orderLine) {
        return false;
    }

    @Override
    public List<OrderLine> findOrderLinesByOrder(Order order) {
        return null;
    }

    @Override
    public long create(Order order) throws ServiceLevelException {
        return 0;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public boolean delete(Order order) {
        return false;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public List<Order> findRange(int start, int perPage) {
        return null;
    }

    @Override
    public Order findById(long id) throws ServiceLevelException {
        return null;
    }

    @Override
    public Integer getNumberOfRecords() throws ServiceLevelException {
        return null;
    }
}

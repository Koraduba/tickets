package epam.pratsaunik.tickets.service.impl;

import epam.pratsaunik.tickets.dao.EntityTransaction;
import epam.pratsaunik.tickets.dao.EventDao;
import epam.pratsaunik.tickets.dao.OrderDao;
import epam.pratsaunik.tickets.dao.impl.EventDaoImpl;
import epam.pratsaunik.tickets.dao.impl.OrderDaoImpl;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Order;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.DaoException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.OrderService;
import epam.pratsaunik.tickets.service.Service;

import java.util.List;

public class OrderServiceImpl implements OrderService, Service {

    @Override
    public List<Order> findOrdersByUser(User user) {
        return null;
    }

    @Override
    public long createOrderLine(OrderLine orderLine) throws ServiceLevelException {
        long id = 0;
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(orderDao);
        try {
            id = orderDao.createOrderLine(orderLine);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return id;
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
        long id = 0;
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(orderDao);
        try {
            id = orderDao.create(order);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return id;
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
        Integer number = null;
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(orderDao);
        try {
            number = orderDao.getNumberOfRecords();
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return number;
    }
}

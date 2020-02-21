package epam.pratsaunik.tickets.service.impl;

import epam.pratsaunik.tickets.dao.EntityTransaction;
import epam.pratsaunik.tickets.dao.EventDao;
import epam.pratsaunik.tickets.dao.OrderDao;
import epam.pratsaunik.tickets.dao.UserDao;
import epam.pratsaunik.tickets.dao.impl.EventDaoImpl;
import epam.pratsaunik.tickets.dao.impl.OrderDaoImpl;
import epam.pratsaunik.tickets.dao.impl.UserDaoImpl;
import epam.pratsaunik.tickets.entity.*;
import epam.pratsaunik.tickets.exception.DaoException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.OrderService;
import epam.pratsaunik.tickets.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService, Service {
    private static Logger log=LogManager.getLogger();

    @Override
    public List<Order> findOrdersByUser(User user) throws ServiceLevelException {
        List<Order> orderList;
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(orderDao);
        try {
            orderList = orderDao.findOrdersByUser(user);
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return orderList;
    }

    @Override
    public List<Order> findOrdersBelowAmount(BigDecimal amount) throws ServiceLevelException {
        List<Order> orderList;
        OrderDao orderDao = new OrderDaoImpl();
        UserDao userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(orderDao, userDao);
        try {
            orderList = orderDao.findOrdersBelowAmount(amount);
            for (Order order : orderList) {
                long id = order.getUser().getUserId();
                order.setUser((User) userDao.findById(id).get(0));
            }
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return orderList;
    }

    @Override
    public List<Order> findOrdersAboveAmount(BigDecimal amount) throws ServiceLevelException {
        List<Order> orderList;
        OrderDao orderDao = new OrderDaoImpl();
        UserDao userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(orderDao, userDao);
        try {
            orderList = orderDao.findOrdersAboveAmount(amount);
            for (Order order : orderList) {
                long id = order.getUser().getUserId();
                order.setUser((User) userDao.findById(id).get(0));
            }
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return orderList;
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
            entityTransaction.rollback();
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
    public List<OrderLine> findOrderLinesByOrder(Order order) throws ServiceLevelException {
        List<OrderLine> orderLines;
        OrderDao orderDao = new OrderDaoImpl();
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(orderDao, eventDao);
        try {
            orderLines = orderDao.findOrderLinesByOrder(order);
            for (OrderLine line : orderLines) {
                long id = line.getTicket().getTicketId();
                log.debug("ticketId"+id);
                line.setTicket(eventDao.findTicketById(id).get(0));
            }
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        }finally {
            entityTransaction.end();
        }

        return orderLines;
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
            entityTransaction.rollback();
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
        Integer number;
        OrderDao orderDao = new OrderDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(orderDao);
        try {
            number = orderDao.getNumberOfRecords();
            entityTransaction.commit();
        } catch (DaoException e) {
            entityTransaction.rollback();
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return number;
    }
}

package epam.pratsaunik.tickets.service.impl;

import epam.pratsaunik.tickets.dao.EntityTransaction;
import epam.pratsaunik.tickets.dao.EventDao;
import epam.pratsaunik.tickets.dao.UserDao;
import epam.pratsaunik.tickets.dao.impl.EventDaoImpl;
import epam.pratsaunik.tickets.dao.impl.UserDaoImpl;
import epam.pratsaunik.tickets.entity.*;
import epam.pratsaunik.tickets.exception.DaoException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.EventService;
import epam.pratsaunik.tickets.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements Service, EventService {
    private final static Logger log = LogManager.getLogger();

    @Override
    public long create(Event event) throws ServiceLevelException {
        long id=0;
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(eventDao);
        try {
            id=eventDao.create(event);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }finally {
            entityTransaction.end();
        }
        return id;
    }

    @Override
    public Venue findVenueById(Long id) throws ServiceLevelException {
        EventDao eventDao = new EventDaoImpl();
        Venue venue = null;
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.begin(eventDao);
            venue = eventDao.findVenueById(id).get(0);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }finally {
            entityTransaction.end();
        }
        return venue;
    }

    @Override
    public Venue findVenueByName(String name) throws ServiceLevelException {
        EventDao eventDao = new EventDaoImpl();
        Venue venue = null;
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.begin(eventDao);
            venue = eventDao.findVenueByName(name).get(0);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }finally {
            entityTransaction.end();
        }
        return venue;
    }

    @Override
    public List<Venue> findAllVenues() throws ServiceLevelException {
        List<Venue> venueList = new ArrayList<>();
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(eventDao);
        try {
            venueList=eventDao.findAllVenues();
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }
        finally {
            entityTransaction.end();
        }
        return venueList;
    }

    @Override
    public long createVenue(Venue venue) throws ServiceLevelException {
        long id=0;
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.begin(eventDao);
            id=eventDao.createVenue(venue);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }finally {
            entityTransaction.end();
        }
        return id;
    }

    @Override
    public boolean createTicket(Ticket ticket) throws ServiceLevelException {

        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(eventDao);
        try {
            eventDao.createTicket(ticket);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }finally {
            entityTransaction.end();
        }
        return true;
    }

    @Override
    public List<Ticket> findTicketsByEvent(Event event) {
        List<Ticket> ticketList=null;
        EventDao eventDao= new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.begin(eventDao);
            ticketList=eventDao.findTicketsByEvent(event);
            entityTransaction.commit();
        } catch (DaoException e) {
            e.printStackTrace();
        }finally {
            entityTransaction.end();
        }
        return ticketList;
    }

    @Override
    public Ticket findTicketById(long id) {
        List<Ticket> ticketList=null;
        EventDao eventDao= new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.begin(eventDao);
            ticketList=eventDao.findTicketById(id);
            entityTransaction.commit();
        } catch (DaoException e) {
            e.printStackTrace();
        }finally {
            entityTransaction.end();
        }
        return ticketList.get(0);
    }

    @Override
    public List<Event> findEventsByRange(int currentPage, int eventsPerPage) throws ServiceLevelException {
        List<Event> events;
        int start = currentPage * eventsPerPage - eventsPerPage;
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(eventDao);
        try {
            events = eventDao.findRange(start, eventsPerPage);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return events;
    }

    @Override
    public Event update(Event event) throws ServiceLevelException {
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(eventDao);
        try {
            eventDao.update(event);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return event;
    }

    @Override
    public Event findEventById(long id) throws ServiceLevelException {
        Event event=null;
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(eventDao);
        try {
            event=(Event)eventDao.findById(id).get(0);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return event;
    }

    @Override
    public Venue updateVenue(Venue venue) throws ServiceLevelException {
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(eventDao);
        try {
            eventDao.updateVenue(venue);
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return venue;
    }


    @Override
    public Integer getNumberOfRecords() throws ServiceLevelException {
        Integer number = null;
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(eventDao);
        try {
            number = eventDao.getNumberOfRecords();
            entityTransaction.commit();
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        } finally {
            entityTransaction.end();
        }
        return number;
    }
}

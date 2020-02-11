package epam.pratsaunik.tickets.service.impl;

import epam.pratsaunik.tickets.dao.EntityTransaction;
import epam.pratsaunik.tickets.dao.EventDao;
import epam.pratsaunik.tickets.dao.impl.EventDaoImpl;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.DaoException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.EventService;
import epam.pratsaunik.tickets.service.Service;

import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements Service, EventService {

    @Override
    public Event create(Event event) throws ServiceLevelException {
        Event savedEvent = null;
        EventDao eventDao = new EventDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.begin(eventDao);
        try {
            savedEvent=(Event)eventDao.create(event);
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }
        entityTransaction.commit();
        return savedEvent;
    }

    @Override
    public Venue findVenueById(Long id) throws ServiceLevelException {
        EventDao eventDao = new EventDaoImpl();
        Venue venue = null;
        EntityTransaction entityTransaction = new EntityTransaction();
        try {
            entityTransaction.begin(eventDao);
            venue = eventDao.findVenueById(id).get(0);
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }
        entityTransaction.commit();
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
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }
        entityTransaction.commit();
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
        } catch (DaoException e) {
            throw new ServiceLevelException(e);
        }
        entityTransaction.commit();
        return venueList;
    }
}

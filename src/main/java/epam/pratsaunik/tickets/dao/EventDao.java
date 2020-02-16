package epam.pratsaunik.tickets.dao;

import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.DaoException;

import java.util.Date;
import java.util.List;

public abstract class EventDao extends AbstractDAO {

    public abstract List<Event> findEventsByDate(Date date);
    public abstract Venue createVenue(Venue venue) throws DaoException;
    public abstract List<Venue> findAllVenues() throws DaoException;
    public abstract List<Venue> findVenueById(Long id) throws DaoException;
    public abstract List<Venue> findVenueByName(String name) throws DaoException;
    public abstract boolean createTicket(Ticket ticket) throws DaoException;
    public abstract List<Event> findEventsByRange(int start, int eventsPerPage) throws DaoException;

}

package epam.pratsaunik.tickets.dao;

import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.DaoException;

import java.util.Date;
import java.util.List;

public abstract class EventDao extends AbstractDAO {

    public abstract List<Event> findEventsByDate(Date date  ) throws DaoException;
    public abstract List<Event> findEventByName(String name) throws DaoException;
    public abstract long createVenue(Venue venue) throws DaoException;
    public abstract Venue updateVenue (Venue venue) throws DaoException;
    public abstract boolean deleteVenue (Venue venue) throws DaoException;
    public abstract List<Venue> findAllVenues() throws DaoException;
    public abstract List<Venue> findVenueById(Long id) throws DaoException;
    public abstract List<Venue> findVenueByName(String name) throws DaoException;
    public abstract long createTicket(Ticket ticket) throws DaoException;
    public abstract boolean deleteTicket(Ticket ticket) throws DaoException;
    public abstract List<Ticket> findTicketById(long id) throws DaoException;
    public abstract List<Ticket> findTicketsByEvent(Event event) throws DaoException;
}

package epam.pratsaunik.tickets.dao;

import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.DaoException;

import java.sql.Date;
import java.util.List;

public abstract class EventDao extends AbstractDAO {

    public abstract List<Event> findEventsByDate(Date date);
    public abstract Venue createVenue(Venue venue);
    public abstract List<Venue> findAllVenues() throws DaoException;
    public abstract List<Venue> findVenueById(Long id) throws DaoException;
    public abstract List<Venue> findVenueByName(String name) throws DaoException;

}

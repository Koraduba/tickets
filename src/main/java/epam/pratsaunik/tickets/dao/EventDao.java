package epam.pratsaunik.tickets.dao;

import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Venue;

import java.sql.Date;
import java.util.List;

public abstract class EventDao extends AbstractDAO {

    public abstract List<Event> findEventsByDate(Date date);
    public abstract Venue create(Venue venue);
}

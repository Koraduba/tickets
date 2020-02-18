package epam.pratsaunik.tickets.service;

import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.ServiceLevelException;

import java.util.List;

public interface EventService {
    public abstract long create(Event event) throws ServiceLevelException;

    public abstract Venue findVenueById(Long id) throws ServiceLevelException;

    public abstract Venue findVenueByName(String name) throws ServiceLevelException;

    public abstract List<Venue> findAllVenues() throws ServiceLevelException;

    public abstract long createVenue(Venue venue) throws ServiceLevelException;

    public abstract boolean createTicket(Ticket ticket) throws ServiceLevelException;

    public abstract List<Ticket> findTicketsByEvent(Event event);

    public abstract Ticket findTicketById(long id);

    public abstract List<Event> findEventsByRange(int start, int eventsPerPage) throws ServiceLevelException;

    public abstract Event update(Event event) throws ServiceLevelException;

    public abstract Event findEventById(long id) throws ServiceLevelException;

    public abstract Venue updateVenue(Venue venue) throws ServiceLevelException;
}

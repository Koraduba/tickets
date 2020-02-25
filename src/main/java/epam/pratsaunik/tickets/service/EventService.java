package epam.pratsaunik.tickets.service;

import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.ServiceLevelException;

import java.util.List;

public interface EventService {
    long create(Event event) throws ServiceLevelException;

    Venue findVenueById(Long id) throws ServiceLevelException;

    Venue findVenueByName(String name) throws ServiceLevelException;

    List<Venue> findAllVenues() throws ServiceLevelException;

    long createVenue(Venue venue) throws ServiceLevelException;

    boolean createTicket(Ticket ticket) throws ServiceLevelException;

    List<Ticket> findTicketsByEvent(Event event) throws ServiceLevelException;

    Ticket findTicketById(long id) throws ServiceLevelException;

    List<Event> findEventsByRange(int start, int eventsPerPage) throws ServiceLevelException;

    List<Event> findEventsByHost (User owner,int currentPage, int eventsPerPage) throws ServiceLevelException;

    Event update(Event event) throws ServiceLevelException;

    Event findEventById(long id) throws ServiceLevelException;

    Venue updateVenue(Venue venue) throws ServiceLevelException;
}

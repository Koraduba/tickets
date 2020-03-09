package epam.pratsaunik.tickets.dao.impl;

import epam.pratsaunik.tickets.dao.ColumnName;
import epam.pratsaunik.tickets.dao.EventDao;
import epam.pratsaunik.tickets.entity.*;
import epam.pratsaunik.tickets.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class EventDaoImpl extends EventDao {
    private final static Logger log = LogManager.getLogger();
    private final static long MILLISECONDS_PER_DAY = 86400000;
    private final static String SQL_GET_NUMBER_OF_RECORDS = "SELECT count(event_id) FROM event";
    private final static String SQL_GET_NUMBER_OF_EVENTS_BY_HOST = "SELECT count(event_id) FROM event WHERE owner=?";
    private final static String SQL_CREATE_EVENT = "INSERT INTO event(name,date,description,image,venue,owner) " +
            "VALUES(?,?,?,?,?,?)";
    private final static String SQL_UPDATE_EVENT = "UPDATE event SET name=?,date=?,description=?,image=?,venue=?,owner=? WHERE event_id=?";
    private final static String SQL_DELETE_EVENT_BY_ID = "DELETE FROM event WHERE event_id=?";
    private final static String SQL_FIND_EVENT_BY_ID = "SELECT event_id,name,date,description,image,venue,owner FROM event WHERE event_id=?";
    private final static String SQL_FIND_ALL_EVENTS = "SELECT event_id,name,date,venue,description,image,owner " +
            "FROM event";
    private final static String SQL_FIND_RANGE_OF_EVENTS = "SELECT event_id,name,date,venue,description,image,owner " +
            "FROM event LIMIT ?,? ";

    private final static String SQL_FIND_EVENTS_BY_DATE = "SELECT event_id,name,date,description,image,venue,owner FROM event WHERE date<? AND date>=?";
    private final static String SQL_FIND_EVENTS_BY_NAME = "SELECT event_id,name,date,description,image,venue,owner FROM event WHERE name=?";
    private final static String SQL_FIND_EVENTS_BY_HOST = "SELECT event_id,name,date,description,image,venue,owner FROM event WHERE owner=? LIMIT ?,?";
    private final static String SQL_CREATE_VENUE = "INSERT INTO venue(name,capacity) " +
            "VALUES(?,?)";
    private final static String SQL_UPDATE_VENUE = "UPDATE venue SET name=?,capacity=? WHERE venue_id=?";
    private final static String SQL_DELETE_VENUE = "DELETE FROM venue WHERE venue_id=?";
    private final static String SQL_FIND_ALL_VENUES = "SELECT venue_id,name,capacity,layout FROM venue";
    private final static String SQL_FIND_VENUE_BY_ID = "SELECT venue_id,name,capacity,layout FROM venue WHERE venue_id=?";
    private final static String SQL_FIND_VENUE_BY_NAME = "SELECT venue_id,name,capacity,layout FROM venue WHERE name=?";
    private final static String SQL_CREATE_TICKET = "INSERT INTO ticket(event,category,price) VALUES(?,?,?)";
    private final static String SQL_DELETE_TICKET = "DELETE FROM ticket WHERE ticket_id=?";
    private final static String SQL_FIND_TICKET_BY_ID = "SELECT ticket_id,event,category.name,price FROM ticket,category " +
            "WHERE category_id=ticket.category " +
            "AND ticket_id=?";
    private final static String SQL_FIND_TICKETS_BY_EVENT = "SELECT ticket_id,event,category.name,price FROM ticket,category " +
            "WHERE category_id=ticket.category AND event=?";
    private final static String SQL_UPDATE_TICKET = "UPDATE ticket SET event=?,category=?,price=? WHERE ticket_id=?";

    @Override
    public int getNumberOfRecords() throws DaoException {
        int result = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_NUMBER_OF_RECORDS);
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return result;
    }

    @Override
    public long create(Event entity) throws DaoException {
        Event event = (Event) entity;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long id = -1;
        try {
            statement = connection.prepareStatement(SQL_CREATE_EVENT, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, ((Event) entity).getName());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            try {
                date = dateFormat.parse((((Event) entity).getDate()) + " " + ((Event) entity).getTime());
                log.debug(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                statement.setLong(2, (date.getTime()));
            } else {
                statement.setNull(2, Types.BIGINT);
            }
            statement.setString(3, event.getDescription());
            statement.setString(4, event.getImage());
            Venue venue = event.getVenue();
            if (venue != null) {
                statement.setLong(5, (venue.getVenueId()));
            } else {
                statement.setNull(5, Types.INTEGER);
            }
            statement.setLong(6, event.getOwner().getUserId());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return id;
    }

    @Override
    public Event update(Event entity) throws DaoException {
        Event event = (Event) entity;
        Event oldEvent = findById(event.getEventId()).get(0);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_EVENT);
            statement.setString(1, event.getName());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            try {
                date = dateFormat.parse((event.getDate()) + " " + event.getTime());
            } catch (ParseException e) {
                throw new DaoException(e);
            }
            statement.setLong(2, (date.getTime()));
            statement.setString(3, event.getDescription());
            statement.setString(4, event.getImage());
            Venue venue = event.getVenue();
            if (venue != null) {
                statement.setLong(5, (venue.getVenueId()));
            } else {
                statement.setNull(5, Types.INTEGER);
            }
            statement.setLong(6, event.getOwner().getUserId());
            statement.setLong(7, event.getEventId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return oldEvent;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_EVENT_BY_ID);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.info("Event cannot be deleted", e);//fixme
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return true;
    }

    @Override
    public List<Event> findById(long id) throws DaoException {
        List<Event> eventList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_EVENT_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getLong(ColumnName.EVENT_ID));
                event.setName(resultSet.getString(ColumnName.EVENT_NAME));
                event.setDescription(resultSet.getString(ColumnName.EVENT_DESCRIPTION));
                event.setImage(resultSet.getString(ColumnName.EVENT_IMAGE));//TODO
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                event.setDate(simpleDateFormat.format(date));
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                event.setTime(simpleDateFormat.format(date));
                List<Venue> venues = findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE));
                if (!venues.isEmpty()) {
                    event.setVenue(venues.get(0));//todo
                }
                User owner = new User();
                owner.setUserId(resultSet.getLong(ColumnName.EVENT_HOST));
                event.setOwner(owner);
                eventList.add(event);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return eventList;
    }


    @Override
    public List findAll() throws DaoException {
        Statement statement = null;
        ResultSet resultSet = null;
        List<Event> eventList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_ALL_EVENTS);
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getLong(ColumnName.EVENT_ID));
                event.setName(resultSet.getString(ColumnName.EVENT_NAME));
                Date date = new Date(resultSet.getLong(ColumnName.EVENT_DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                event.setDate(simpleDateFormat.format(date));
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                event.setTime(simpleDateFormat.format(date));
                event.setDescription(resultSet.getString(ColumnName.EVENT_DESCRIPTION));
                event.setImage(resultSet.getString(ColumnName.EVENT_IMAGE));
                event.setVenue(findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE)).get(0));
                User owner = new User();
                owner.setUserId(resultSet.getLong(ColumnName.EVENT_HOST));
                event.setOwner(owner);
                eventList.add(event);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return eventList;
    }

    @Override
    public List<Event> findRange(int start, int recordsPerPage) throws DaoException {
        List<Event> events = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_RANGE_OF_EVENTS);
            statement.setInt(1, start);
            statement.setInt(2, recordsPerPage);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getLong(ColumnName.EVENT_ID));
                event.setName(resultSet.getString(ColumnName.EVENT_NAME));
                event.setDescription(resultSet.getString(ColumnName.EVENT_DESCRIPTION));
                event.setImage(resultSet.getString(ColumnName.EVENT_IMAGE));//TODO
                Date date = new Date(resultSet.getLong(ColumnName.EVENT_DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                event.setDate(simpleDateFormat.format(date));
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                event.setTime(simpleDateFormat.format(date));
                List<Venue> venues = findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE));
                if (!venues.isEmpty()) {
                    event.setVenue(venues.get(0));//todo
                }
                User owner = new User();
                owner.setUserId(resultSet.getLong(ColumnName.EVENT_HOST));
                event.setOwner(owner);
                events.add(event);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return events;
    }


    @Override
    public Integer getNumberOfEventsByHost(User owner) throws DaoException {
        Integer number =null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            statement=connection.prepareStatement(SQL_GET_NUMBER_OF_EVENTS_BY_HOST);
            statement.setLong(1,owner.getUserId());
            resultSet=statement.executeQuery();
            while (resultSet.next()){
                number=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw  new DaoException(e);
        }finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return number;
    }

    @Override
    public List<Event> findEventsByDate(Date dateOfEvent) throws DaoException {
        List<Event> eventList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long time = dateOfEvent.getTime();
        try {
            statement = connection.prepareStatement(SQL_FIND_EVENTS_BY_DATE);
            statement.setLong(1, time / MILLISECONDS_PER_DAY * MILLISECONDS_PER_DAY);
            statement.setLong(2, time / MILLISECONDS_PER_DAY * MILLISECONDS_PER_DAY + MILLISECONDS_PER_DAY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getLong(ColumnName.EVENT_ID));
                event.setName(resultSet.getString(ColumnName.EVENT_NAME));
                event.setDescription(resultSet.getString(ColumnName.EVENT_DESCRIPTION));
                event.setImage(resultSet.getString(ColumnName.EVENT_IMAGE));//TODO
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                event.setDate(simpleDateFormat.format(date));
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                event.setTime(simpleDateFormat.format(date));
                List<Venue> venues = findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE));
                if (!venues.isEmpty()) {
                    event.setVenue(venues.get(0));//todo
                }
                User owner = new User();
                owner.setUserId(resultSet.getLong(ColumnName.EVENT_HOST));
                event.setOwner(owner);
                eventList.add(event);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return eventList;
    }

    @Override
    public List<Event> findEventByName(String name) throws DaoException {
        List<Event> eventList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_EVENTS_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getLong(ColumnName.EVENT_ID));
                event.setName(resultSet.getString(ColumnName.EVENT_NAME));
                event.setDescription(resultSet.getString(ColumnName.EVENT_DESCRIPTION));
                event.setImage(resultSet.getString(ColumnName.EVENT_IMAGE));//TODO
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                event.setDate(simpleDateFormat.format(date));
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                event.setTime(simpleDateFormat.format(date));
                List<Venue> venues = findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE));
                if (!venues.isEmpty()) {
                    event.setVenue(venues.get(0));//todo
                }
                User owner = new User();
                owner.setUserId(resultSet.getLong(ColumnName.EVENT_HOST));
                event.setOwner(owner);
                eventList.add(event);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return eventList;
    }

    @Override
    public List<Event> findEventByHost(User owner, int start, int recordsPerPage) throws DaoException {
        List<Event> eventList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_EVENTS_BY_HOST);
            statement.setLong(1, owner.getUserId());
            statement.setInt(2, start);
            statement.setInt(3, recordsPerPage);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getLong(ColumnName.EVENT_ID));
                event.setName(resultSet.getString(ColumnName.EVENT_NAME));
                event.setDescription(resultSet.getString(ColumnName.EVENT_DESCRIPTION));
                event.setImage(resultSet.getString(ColumnName.EVENT_IMAGE));//TODO
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                event.setDate(simpleDateFormat.format(date));
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                event.setTime(simpleDateFormat.format(date));
                List<Venue> venues = findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE));
                if (!venues.isEmpty()) {
                    event.setVenue(venues.get(0));//todo
                }
                event.setOwner(owner);
                eventList.add(event);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return eventList;
    }

    @Override
    public long createVenue(Venue venue) throws DaoException {
        long id = 0;
        log.debug("EventDaOImpl::createVenue");
        log.debug(venue.toString());
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_VENUE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, venue.getName());
            statement.setInt(2, venue.getCapacity());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong(1);
            venue.setVenueId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return id;
    }

    @Override
    public Venue updateVenue(Venue venue) throws DaoException {
        Venue oldVenue = findVenueById(venue.getVenueId()).get(0);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_VENUE);
            statement.setString(1, venue.getName());
            statement.setInt(2, venue.getCapacity());
            statement.setLong(3, venue.getVenueId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return oldVenue;
    }

    @Override
    public boolean deleteVenue(Venue venue) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_VENUE);
            statement.setLong(1, venue.getVenueId());
            statement.execute();
        } catch (SQLException e) {
            log.info("Venue cannot be deleted", e);//fixme
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return true;
    }


    @Override
    public List<Venue> findAllVenues() throws DaoException {
        Statement statement = null;
        ResultSet resultSet = null;
        List<Venue> venueList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_FIND_ALL_VENUES);
            while (resultSet.next()) {
                Venue venue = new Venue();
                venue.setVenueId(resultSet.getLong(ColumnName.VENUE_ID));
                venue.setName(resultSet.getString(ColumnName.VENUE_NAME));
                venue.setCapacity(resultSet.getInt(ColumnName.VENUE_CAPACITY));
                venueList.add(venue);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return venueList;
    }

    @Override
    public List<Venue> findVenueById(Long id) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Venue> venueList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQL_FIND_VENUE_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Venue venue = new Venue();
                venue.setVenueId(resultSet.getLong(ColumnName.VENUE_ID));
                venue.setName(resultSet.getString(ColumnName.VENUE_NAME));
                venue.setCapacity(resultSet.getInt(ColumnName.VENUE_CAPACITY));
                venueList.add(venue);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return venueList;
    }

    @Override
    public List<Venue> findVenueByName(String name) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Venue> venueList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQL_FIND_VENUE_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Venue venue = new Venue();
                venue.setVenueId(resultSet.getLong(ColumnName.VENUE_ID));
                venue.setName(resultSet.getString(ColumnName.VENUE_NAME));
                venue.setCapacity(resultSet.getInt(ColumnName.VENUE_CAPACITY));
                venueList.add(venue);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return venueList;
    }

    @Override
    public long createTicket(Ticket ticket) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long id = 0;
        try {
            statement = connection.prepareStatement(SQL_CREATE_TICKET, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, ticket.getEvent().getEventId());
            statement.setInt(2, ticket.getCategory().ordinal() + 1);
            statement.setBigDecimal(3, ticket.getPrice());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong(1);
            ticket.setTicketId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }

        return id;
    }

    @Override
    public boolean deleteTicket(Ticket ticket) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_TICKET);
            statement.setLong(1, ticket.getTicketId());
            statement.execute();
        } catch (SQLException e) {
            log.info("Event ccannot be deleted", e);//fixme
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return true;
    }

    @Override
    public List<Ticket> findTicketById(long id) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ticket> ticketList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQL_FIND_TICKET_BY_ID);
            statement.setLong(1, id);
            log.debug("Statement:" + statement);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getLong(ColumnName.TICKET_ID));
                Event event = findById(resultSet.getLong(ColumnName.TICKET_EVENT)).get(0);
                ticket.setEvent(event);
                ticket.setPrice(resultSet.getBigDecimal(ColumnName.TICKET_PRICE));
                ticket.setCategory(TicketCat.valueOf(resultSet.getString(ColumnName.TICKET_CATEGORY)));
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return ticketList;
    }

    @Override
    public List<Ticket> findTicketsByEvent(Event event) throws DaoException {
        log.debug(event);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ticket> ticketList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQL_FIND_TICKETS_BY_EVENT);
            statement.setLong(1, event.getEventId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getLong(ColumnName.TICKET_ID));
                ticket.setEvent(event);
                ticket.setPrice(resultSet.getBigDecimal(ColumnName.TICKET_PRICE));
                ticket.setCategory(TicketCat.valueOf(resultSet.getString(ColumnName.TICKET_CATEGORY)));
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }
        return ticketList;
    }

    @Override
    public Ticket updateTicket(Ticket ticket) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_TICKET);
            statement.setLong(1, ticket.getEvent().getEventId());
            statement.setInt(2, ticket.getCategory().ordinal() + 1);
            statement.setBigDecimal(3, ticket.getPrice());
            statement.setLong(4, ticket.getTicketId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.warn(e);
            }
        }

        return ticket;
    }


}

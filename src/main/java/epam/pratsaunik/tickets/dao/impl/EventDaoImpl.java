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
    private final static long MILLISECONDS_PER_DAY=86400000;
    private final static String SQL_GET_NUMBER_OF_RECORDS = "SELECT count(event_id) FROM event";
    private final static String SQL_CREATE_EVENT = "INSERT INTO event(name,date,description,image,venue) " +
            "VALUES(?,?,?,?,?)";
    private final static String SQL_UPDATE_EVENT = "UPDATE event SET name=?,date=?,description=?,image=?,venue=? WHERE event_id=?";
    private final static String SQL_DELETE_EVENT_BY_ID = "DELETE FROM event WHERE event_id=?";
    private final static String SQL_FIND_EVENT_BY_ID = "SELECT event_id,name,date,description,image,venue FROM event WHERE event_id=?";
    private final static String SQL_FIND_ALL_EVENTS = "SELECT event_id,name,date,venue,description,image " +
            "FROM event";
    private final static String SQL_FIND_RANGE_OF_EVENTS = "SELECT event_id,name,date,venue,description,image " +
            "FROM event LIMIT ?,?";

    private final static String SQL_FIND_EVENTS_BY_DATE = "SELECT event_id,name,date,description,image,venue FROM event WHERE date<? AND date>=?";
    private final static String SQL_FIND_EVENTS_BY_NAME = "SELECT event_id,name,date,description,image,venue FROM event WHERE name=?";
    private final static String SQL_CREATE_VENUE = "INSERT INTO venue(name,capacity,layout) " +
            "VALUES(?,?,?)";
    private final static String SQL_UPDATE_VENUE = "UPDATE venue SET name=?,capacity=?,layout=? WHERE venue_id=?";
    private final static String SQL_DELETE_VENUE = "DELETE FROM venue WHERE venue_id=?";
    private final static String SQL_FIND_ALL_VENUES = "SELECT venue_id,name,capacity,layout FROM venue";
    private final static String SQL_FIND_VENUE_BY_ID = "SELECT venue_id,name,capacity,layout FROM venue WHERE venue_id=?";
    private final static String SQL_FIND_VENUE_BY_NAME = "SELECT venue_id,name,capacity,layout FROM venue WHERE name=?";
    private final static String SQL_CREATE_TICKET = "INSERT INTO ticket(event,category,price) VALUES(?,?,?)";
    private final static String SQL_DELETE_TICKET="DELETE FROM ticket WHERE ticket_id=?";
    private final static String SQL_FIND_TICKET_BY_ID="SELECT ticket_id,event,category.name,price, FROM ticket,category WHERE category_id=ticket.category " +
            "AND ticket_id=?";
    private final static String SQL_FIND_TICKETS_BY_EVENT="SELECT ticket_id,event,category.name,price FROM ticket,category WHERE category_id=ticket.category AND event=?";

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
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                throw new DaoException();
            }
        }
        return result;
    }

    @Override
    public long create(Entity entity) throws DaoException {
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
            statement.setLong(2, (date.getTime()));
            statement.setString(3, event.getDescription());
            statement.setString(4, event.getImage());
            Venue venue = event.getVenue();
            if (venue != null) {
                statement.setLong(5, (venue.getVenueId()));
            } else {
                statement.setLong(5, 0);
            }
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return id;
    }

    @Override
    public Entity update(Entity entity) throws DaoException {
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
            statement.setLong(6, event.getEventId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
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
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return true;
    }

    @Override
    public List<Event> findById(long id) throws DaoException {
        List<Event> eventList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_EVENT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
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
                Venue venue = findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE)).get(0);
                event.setVenue(venue);
                eventList.add(event);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return eventList;
    }


    @Override
    public List findAll() throws DaoException {
        Statement statement = null;
        List<Event> eventList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_EVENTS);
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
                eventList.add(event);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return eventList;
    }

    @Override
    public List findRange(int start, int recordsPerPage) throws DaoException {
        List<Event> events = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_RANGE_OF_EVENTS);
            statement.setInt(1, start);
            statement.setInt(2, recordsPerPage);
            ResultSet resultSet = statement.executeQuery();
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
                Venue venue = findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE)).get(0);
                event.setVenue(venue);
                events.add(event);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return events;
    }


    @Override
    public List<Event> findEventsByDate(Date dateOfEvent) throws DaoException {
        List<Event> eventList = new ArrayList<>();
        PreparedStatement statement = null;
        long time=dateOfEvent.getTime();
        try {
            statement = connection.prepareStatement(SQL_FIND_EVENTS_BY_DATE);
            statement.setLong(1, time/MILLISECONDS_PER_DAY*MILLISECONDS_PER_DAY);
            statement.setLong(2,time/MILLISECONDS_PER_DAY*MILLISECONDS_PER_DAY+MILLISECONDS_PER_DAY);
            ResultSet resultSet = statement.executeQuery();
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
                Venue venue = findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE)).get(0);
                event.setVenue(venue);
                eventList.add(event);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return eventList;
    }

    @Override
    public List<Event> findEventByName(String name) throws DaoException {
        List<Event> eventList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_EVENTS_BY_NAME);
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
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
                Venue venue = findVenueById(resultSet.getLong(ColumnName.EVENT_VENUE)).get(0);
                event.setVenue(venue);
                eventList.add(event);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return eventList;
    }

    @Override
    public long createVenue(Venue venue) throws DaoException {
        long id=0;
        log.debug("EventDaOImpl::createVenue");
        log.debug(venue.toString());
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_VENUE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, venue.getName());
            statement.setInt(2, venue.getCapacity());
            if (venue.getLayout() != null) {
                statement.setString(3, venue.getLayout());
            } else {
                statement.setNull(3, Types.VARCHAR);
            }
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id=resultSet.getLong(1);
            venue.setVenueId(id);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
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
            if (venue.getLayout() != null) {
                statement.setString(3, venue.getLayout());
            } else {
                statement.setNull(3, Types.VARCHAR);
            }
            statement.setLong(4,venue.getVenueId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
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
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return true;
    }


    @Override
    public List<Venue> findAllVenues() throws DaoException {
        Statement statement = null;
        List<Venue> venueList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_VENUES);
            while (resultSet.next()) {
                Venue venue = new Venue();
                venue.setVenueId(resultSet.getLong(ColumnName.VENUE_ID));
                venue.setName(resultSet.getString(ColumnName.VENUE_NAME));
                venue.setCapacity(resultSet.getInt(ColumnName.VENUE_CAPACITY));
                venue.setLayout(resultSet.getString(ColumnName.VENUE_NAME));
                venueList.add(venue);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return venueList;
    }

    @Override
    public List<Venue> findVenueById(Long id) throws DaoException {
        PreparedStatement statement = null;
        List<Venue> venueList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQL_FIND_VENUE_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Venue venue = new Venue();
                venue.setVenueId(resultSet.getLong(ColumnName.VENUE_ID));
                venue.setName(resultSet.getString(ColumnName.VENUE_NAME));
                venue.setCapacity(resultSet.getInt(ColumnName.VENUE_CAPACITY));
                venue.setLayout(resultSet.getString(ColumnName.VENUE_LAYOUT));//todo
                venueList.add(venue);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return venueList;
    }

    @Override
    public List<Venue> findVenueByName(String name) throws DaoException {
        PreparedStatement statement = null;
        List<Venue> venueList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQL_FIND_VENUE_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Venue venue = new Venue();
                venue.setVenueId(resultSet.getLong(ColumnName.VENUE_ID));
                venue.setName(resultSet.getString(ColumnName.VENUE_NAME));
                venue.setCapacity(resultSet.getInt(ColumnName.VENUE_CAPACITY));
                venue.setLayout(resultSet.getString(ColumnName.VENUE_LAYOUT));
                venueList.add(venue);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return venueList;
    }

    @Override
    public long createTicket(Ticket ticket) throws DaoException {
        PreparedStatement statement = null;
        long id=0;
        try {
            statement = connection.prepareStatement(SQL_CREATE_TICKET, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, ticket.getEvent().getEventId());
            statement.setInt(2, ticket.getCategory().ordinal() + 1);
            statement.setBigDecimal(3, ticket.getPrice());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id=resultSet.getLong(1);
            ticket.setTicketId(id);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
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
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return true;
    }

    @Override
    public List<Ticket> findTicketById(long id) throws DaoException {
        PreparedStatement statement = null;
        List<Ticket> ticketList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQL_FIND_TICKET_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getLong(ColumnName.TICKET_ID));
                Event event =findById(resultSet.getLong(ColumnName.TICKET_EVENT)).get(0);
                ticket.setEvent(event);
                ticket.setPrice(resultSet.getBigDecimal(ColumnName.TICKET_PRICE));
                ticket.setCategory(TicketCat.valueOf(resultSet.getString(ColumnName.TICKET_CATEGORY)));
                ticketList.add(ticket);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return ticketList;
    }

    @Override
    public List<Ticket> findTicketsByEvent(Event event) throws DaoException {
        PreparedStatement statement = null;
        List<Ticket> ticketList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQL_FIND_TICKETS_BY_EVENT);
            statement.setLong(1, event.getEventId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getLong(ColumnName.TICKET_ID));
                ticket.setEvent(event);
                ticket.setPrice(resultSet.getBigDecimal(ColumnName.TICKET_PRICE));
                ticket.setCategory(TicketCat.valueOf(resultSet.getString(ColumnName.TICKET_CATEGORY)));
                ticketList.add(ticket);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return ticketList;
    }


}

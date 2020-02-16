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
    private final static String SQL_CREATE_VENUE = "INSERT INTO venue(name,capacity) " +
            "VALUES(?,?)";
    private final static String SQL_FIND_ALL_VENUES = "SELECT venue_id,name,capacity,layout FROM venue";
    private final static String SQL_FIND_VENUE_BY_ID = "SELECT venue_id,name,capacity,layout FROM venue WHERE venue_id=?";
    private final static String SQL_FIND_VENUE_BY_NAME = "SELECT venue_id,name,capacity,layout FROM venue WHERE name=?";
    private final static String SQL_CREATE_EVENT = "INSERT INTO event(name,date,description,image,venue) " +
            "VALUES(?,?,?,?,?)";
    private final static String SQL_FIND_ALL_EVENTS = "SELECT event_id,name,date,venue,description " +
            "FROM event";
    private final static String SQL_FIND_EVENTS_BY_RANGE = "SELECT event_id,name,date,venue,description " +
            "FROM event LIMIT ?,?";
    private final static String SQL_CREATE_TICKET = "INSERT INTO ticket(event,category,price) VALUES(?,?,?)";
    private final static String SQL_GET_NUMBER_OF_RECORDS = "SELECT count(event_id) FROM event";


    @Override
    public List<Event> findEventsByDate(Date date) {
        return null;
    }

    @Override
    public Venue createVenue(Venue venue) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_VENUE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, venue.getName());
            statement.setInt(2, venue.getCapacity());//todo

//            statement.setString(3, venue.getLayout());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            venue.setVenueId(resultSet.getLong(1));
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
        return venue;
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
                venue.setVenueId(resultSet.getLong("venue_id"));
                venue.setName(resultSet.getString("name"));
                venue.setCapacity(resultSet.getInt("capacity"));
                venue.setLayout(resultSet.getString("layout"));
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
                venue.setVenueId(resultSet.getLong("venue_id"));
                venue.setName(resultSet.getString("name"));
                venue.setCapacity(resultSet.getInt("capacity"));
                //venue.setLayout(resultSet.getString("layout"));//todo
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
                venue.setVenueId(resultSet.getLong("venue_id"));
                venue.setName(resultSet.getString("name"));
                venue.setCapacity(resultSet.getInt("capacity"));
                venue.setLayout(resultSet.getString("layout"));
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
    public boolean createTicket(Ticket ticket) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_TICKET, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, ticket.getEvent().getEventId());
            statement.setInt(2, ticket.getCategory().ordinal() + 1);
            statement.setBigDecimal(3, ticket.getPrice());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            ticket.setTicketId(resultSet.getLong(1));
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public List<Event> findEventsByRange(int start, int eventsPerPage) throws DaoException {
        List<Event> events = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_EVENTS_BY_RANGE);
            statement.setInt(1, start);
            statement.setInt(2, eventsPerPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getLong(ColumnName.EVENT_ID));
                event.setName(resultSet.getString(ColumnName.EVENT_NAME));
                event.setDescription(resultSet.getString(ColumnName.EVENT_DESCRIPTION));
                //event.setImage(resultSet.getString(ColumnName.EVENT_IMAGE));//TODO
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                event.setDate(simpleDateFormat.format(date));
                simpleDateFormat= new SimpleDateFormat("HH:mm");
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
                e.printStackTrace();
            }
        }
        return events;
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
                event.setEventId(resultSet.getLong("event_id"));
                event.setName(resultSet.getString("name"));
                Date date = new Date(resultSet.getLong("date"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                event.setDate(simpleDateFormat.format(date));
                simpleDateFormat= new SimpleDateFormat("HH:mm");
                event.setTime(simpleDateFormat.format(date));
                event.setDescription(resultSet.getString("description"));
                event.setImage(resultSet.getString("image"));
                event.setVenue(findVenueById(resultSet.getLong("venue")).get(0));
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
        return null;
    }

    @Override
    public List findRange(int start, int recordsPerPage) throws DaoException {
        return null;
    }

    @Override
    public int getNumberOfRecords() throws DaoException {
        int result = 0;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_NUMBER_OF_RECORDS);
            while (resultSet.next()) {
                result = resultSet.getInt(1);
                log.debug("NrOfRows: " + result);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException();
            }
        }
        return result;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }

    @Override
    public long create(Entity entity) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long id = -1;
        try {
            statement = connection.prepareStatement(SQL_CREATE_EVENT, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, ((Event) entity).getName());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date=null;
            try {
                date = dateFormat.parse((((Event)entity).getDate())+" "+((Event)entity).getTime());
                log.debug(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            statement.setLong(2, (date.getTime()));
            statement.setString(3, ((Event) entity).getDescription());
            statement.setString(4, ((Event) entity).getImage());
            Venue venue = ((Event) entity).getVenue();
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
    public Entity update(Entity entity) {
        return null;
    }
}

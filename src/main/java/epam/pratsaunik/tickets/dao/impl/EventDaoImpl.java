package epam.pratsaunik.tickets.dao.impl;

import epam.pratsaunik.tickets.dao.EventDao;
import epam.pratsaunik.tickets.entity.Entity;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDaoImpl extends EventDao {

    private final static String SQL_CREATE_VENUE = "INSERT INTO venue(name,capacity,layout) " +
            "VALUES(?,?,?)";
    private final static String SQL_FIND_ALL_VENUES = "SELECT venue_id,name,capacity,layout FROM venue";
    private final static String SQL_FIND_VENUE_BY_ID ="SELECT venue_id,name,capacity,layout FROM venue WHERE venue_id=?";
    private final static String SQL_FIND_VENUE_BY_NAME ="SELECT venue_id,name,capacity,layout FROM venue WHERE name=?";
    private final static String SQL_CREATE_EVENT = "INSERT INTO event(name,date,description,image,venue) " +
            "VALUES(?,?,?,?,?)";
    private final static String SQL_FIND_ALL_EVENTS = "SELECT event_id,event.name,date,venue.name,description " +
            "FROM event,venue WHERE venue=venue_id";



    @Override
    public List<Event> findEventsByDate(Date date) {
        return null;
    }

    @Override
    public Venue createVenue(Venue venue) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_VENUE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, venue.getName());
            statement.setInt(2, venue.getCapacity());
            statement.setString(3, venue.getLayout());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            venue.setVenueId(resultSet.getLong(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
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
        PreparedStatement statement=null;
        List<Venue> venueList = new ArrayList<>();
        try {
            statement=connection.prepareStatement(SQL_FIND_VENUE_BY_ID);
            statement.setLong(1,id);
            ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){
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
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return null;
    }

    @Override
    public List<Venue> findVenueByName(String name) throws DaoException {
        PreparedStatement statement=null;
        List<Venue> venueList = new ArrayList<>();
        try {
            statement=connection.prepareStatement(SQL_FIND_VENUE_BY_NAME);
            statement.setString(1,name);
            ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){
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
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return venueList;
    }

    @Override
    public List findAll() throws DaoException {
        Statement statement = null;
        List<Event> eventList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_EVENTS);
            while(resultSet.next()){
                Event event = new Event();
                event.setEventId(resultSet.getLong("event_id"));
                event.setName(resultSet.getString("name"));
                event.setDate(resultSet.getTimestamp("date"));
                event.setDescription(resultSet.getString("description"));
                event.setImage(resultSet.getString("image"));
                event.setVenue(findVenueById(resultSet.getLong("venue")).get(0));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return null;
    }

    @Override
    public List findRange(int start, int recordsPerPage) throws DaoException {
        return null;
    }

    @Override
    public int getNumberOfRecords() throws DaoException {
        return 0;
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
    public Event create(Entity entity) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_EVENT, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, ((Event) entity).getName());
            statement.setTimestamp(2, (new Timestamp(((Event) entity).getDate().getTime())));
            statement.setString(3, ((Event) entity).getDescription());
            statement.setString(4, ((Event) entity).getImage());
            Venue venue = ((Event) entity).getVenue();
            if (venue!=null){
                statement.setLong(5, (venue.getVenueId()));
            }else {
                statement.setLong(5,0);
            }
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            ((Event) entity).setEventId(resultSet.getLong(1));
            resultSet.close();//TODO
        } catch (SQLException e) {
           throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return (Event) entity;
    }

    @Override
    public Entity update(Entity entity) {
        return null;
    }
}

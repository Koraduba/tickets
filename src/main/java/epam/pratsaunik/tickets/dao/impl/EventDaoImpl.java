package epam.pratsaunik.tickets.dao.impl;

import epam.pratsaunik.tickets.dao.EventDao;
import epam.pratsaunik.tickets.entity.Entity;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.DaoException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EventDaoImpl extends EventDao {

    private final static String SQL_CREATE_VENUE = "INSERT INTO venue(name,capacity) " +
            "VALUES(?,?)";
    private final static String SQL_CREATE_EVENT = "INSERT INTO event(event_date,venue,description) " +
            "VALUES(?,?,?)";

    @Override
    public List<Event> findEventsByDate(Date date) {
        return null;
    }

    @Override
    public Venue create(Venue venue) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_VENUE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, venue.getName());
            statement.setInt(2, venue.getCapacity());
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
    public List findAll() {
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
    public Event create(Entity entity) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_EVENT, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDate(1, ((Event) entity).getDate());
            Venue venue=((Event) entity).getVenue();
            statement.setLong(2, (venue.getVenueId()));
            statement.setString(3,((Event) entity).getDescription());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            ((Event) entity).setEventId(resultSet.getLong(1));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (Event)entity;
    }

    @Override
    public Entity update(Entity entity) {
        return null;
    }
}

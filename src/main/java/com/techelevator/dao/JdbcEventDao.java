package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.DanceHall;
import com.techelevator.model.Event;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.Principal;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcEventDao implements EventDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcEventDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //create and add a new event to the list - manager or Admin only
    @Override
    public Event createEvent(Event newEvent) {

        Event event = null;
        String sql = "INSERT INTO events " +
                "(event_name, event_date, event_description, dance_hall_id, user_id) VALUES (?, ?, ?, ?, ?) RETURNING event_id; ";

        try {
            int newEventId = jdbcTemplate.queryForObject(sql, int.class,
                    newEvent.getEventName(),
                    newEvent.getEventDate(),
                    newEvent.getEventDescription(),
                    newEvent.getDanceHallId(),
                    newEvent.getUserId());

            event = getEventById(newEventId);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return event;
    }

    //retrieve a list of all events - everyone
    @Override
    public List<Event> getEvents() {

        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events ORDER BY dance_hall_id";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                events.add(mapRowToEvent(results));}
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return events;
    }

    //retrieve an existing event by event id - everyone
    @Override
    public Event getEventById(int id) {

        Event event = null;
        String sql = "SELECT * FROM events WHERE event_id = ? ;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                event = mapRowToEvent(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return event;
    }

    //will update in next stage
    //retrieve a list of all events for a dance hall by dance hall id
    @Override
    public List<Event> getEventsByDanceHallId(int danceHallId) {

        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE dance_hall_id = ? ORDER BY event_id";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, danceHallId);
            while (results.next()) {
                events.add(mapRowToEvent(results));
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return events;
    }

    //update an existing event in the list by event id - manager or admin only
    @Override
    public Event updateEvent(Event updatedEvent) {

        String sql = "UPDATE events " +
                " SET event_name=?, event_date=?, event_description=?, dance_hall_id=?, user_id=? " +
                "WHERE event_id=?;";
        try {
            int rowsAffected =
                    jdbcTemplate.update(sql,
                            updatedEvent.getEventName(),
                            updatedEvent.getEventDate(),
                            updatedEvent.getEventDescription(),
                            updatedEvent.getDanceHallId(),
                            updatedEvent.getUserId(),
                            updatedEvent.getEvent_id());
            if(rowsAffected == 0){
                throw new DaoException( "Error updating Event");
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getEventById(updatedEvent.getEvent_id());
    }

    //delete an event from the list by event id - manager or admin only
    @Override
    public int deleteEventById(int id) {

        String sql1 = "DELETE FROM events WHERE event_id= ? ;";

        try {
            int rowsAffected =
            jdbcTemplate.update(sql1,id);

        if (rowsAffected ==0)
            throw new DaoException("Error deleting event" + id);

            return rowsAffected;
        }
        catch (DataIntegrityViolationException e){
            throw new DaoException("Error deleting event " + id, e);
        }
        catch (DataAccessException e){
            throw new DaoException("Unable to delete event" + id, e);
        }
    }

    private Event mapRowToEvent(SqlRowSet rowSet){
        Event event = new Event();
        event.setEvent_id(rowSet.getInt("event_id"));
        event.setEventName(rowSet.getString("event_name"));
        event.setEventDate(rowSet.getDate("event_date"));
        event.setEventDescription(rowSet.getString("event_description"));
        event.setDanceHallId(rowSet.getInt("dance_hall_id"));
        event.setUserId(rowSet.getInt("user_id"));
        return event;
    }
}

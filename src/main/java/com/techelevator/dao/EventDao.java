package com.techelevator.dao;

import com.techelevator.model.DanceHall;
import com.techelevator.model.Event;

import java.util.List;

public interface EventDao {

        //create and add a new event to the list - manager or Admin only
        Event createEvent(Event event);

        //retrieve a list of all events - everyone
        List<Event> getEvents();

        //retrieve information on an existing event by event id - everyone
        Event getEventById(int id);

        //will update in next stage
        //retrieve a list of all events for a dance hall by dance hall id
        List<Event> getEventsByDanceHallId(int danceHallId);

        //update an existing event in the list by event id - manager or admin only
        Event updateEvent(Event event);

        //delete an event from the list by event id - manager or admin only
        int deleteEventById(int id);

        //check that the manager is assigned to that specific dance hall
        //public boolean isManagerOfDanceHall(int danceHallId, int managerId);

}

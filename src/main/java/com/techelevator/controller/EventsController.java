package com.techelevator.controller;

import com.techelevator.dao.EventDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.DanceHall;
import com.techelevator.model.Event;
import com.techelevator.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/events")

public class EventsController {

    private EventDao eventDao;

    public EventsController(EventDao eventDao){
        this.eventDao = eventDao;
    }

    //create a new event and add it to the list - manager or Admin only
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Event add(@Valid @RequestBody Event event, Principal principal) {
        System.out.println(principal.getName());
        return eventDao.createEvent(event);
    }

    //retrieve a list of all events - everyone
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Event> list() {
        return eventDao.getEvents();
    }

    //retrieve information on an existing event by event id - everyone
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Event get(@PathVariable int id) {
        Event event = eventDao.getEventById(id);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        else {
            return event;
        }
    }

    //will update in next stage
    //retrieve a list of all events for a dance hall by dance hall id
    //List<Event> getEventsByDanceHallId(int danceHallId);

    //update an existing event in the list by event id - manager or admin only
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Event update(@Valid @RequestBody Event event, @PathVariable int id) {

        //check that the event exists
        Event existingEvent = eventDao.getEventById(id);
        if (existingEvent == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found");
        }

        // The id on the path takes precedence over the id in the request body, if any
        event.setEvent_id(id);
        try {
            Event updatedEvent = eventDao.updateEvent(event);
            return updatedEvent;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    //delete an event from the list by event id - manager or admin only
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        if (eventDao.getEventById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found");
        }
        eventDao.deleteEventById(id);
    }

}

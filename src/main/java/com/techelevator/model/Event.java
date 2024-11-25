package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class Event {

    //event id
    private int event_id;

    //event name
    @NotBlank(message = "The field `Event Name` should not be blank.")
    private String eventName;
    @NotNull(message = "The field `Event Date` should not be null.")
    private Date eventDate;
    @NotBlank(message = "The field `Event Description` should not be blank.")
    private String eventDescription;
    @NotNull(message = "The field `Dance Hall Id` should not be null.")
    private int danceHallId;
    @NotNull(message = "The field `User Id` should not be null.")
    private  int userId;

    public Event(){}

    //event constructor
    public Event(int event_id, String eventName, Date eventDate, String eventDescription, int danceHallId, int userId) {
        this.event_id = event_id;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
        this.danceHallId = danceHallId;
        this.userId = userId;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public int getDanceHallId() {
        return danceHallId;
    }

    public void setDanceHallId(int danceHallId) {
        this.danceHallId = danceHallId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event_id=" + event_id +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}

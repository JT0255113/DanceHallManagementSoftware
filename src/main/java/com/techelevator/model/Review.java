package com.techelevator.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a review for an event.
 */
public class Review {

    private int reviewId;
    @NotNull
    private int eventId;
    @NotNull
    @Size(max=255)
    private String reviewText;
    @Min(1)
    @Max(5)
    private int eventRating;


    //constructor
    public Review(){

    }

    public Review(int reviewId, int eventId, String reviewText, int eventRating) {
        this.reviewId = reviewId;
        this.eventId = eventId;
        this.reviewText = reviewText;
        this.eventRating = eventRating;
    }


    //getters and setters

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getEventRating() {
        return eventRating;
    }

    public void setEventRating(int eventRating) {
        this.eventRating = eventRating;
    }

}

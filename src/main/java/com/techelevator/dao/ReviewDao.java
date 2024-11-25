package com.techelevator.dao;

import com.techelevator.model.Event;
import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {

        //create and add a new review for an event - registered users and admin
        Review createReview(Review review);

        //retrieve a list of all reviews - everyone
        List<Review> getReviews();

        //retrieve an existing review by review id - everyone
        Review getReviewById(int id);

        //will update in the next stage
        //retrieve a list of reviews for an event by event id - everyone
        List<Review> getReviewsByEventId(int eventId);

        //update an existing review by review id - registered users and admin
        Review updateReview(Review review);

        //delete an existing review by review id - admin only
        void deleteReviewById(int id);


}

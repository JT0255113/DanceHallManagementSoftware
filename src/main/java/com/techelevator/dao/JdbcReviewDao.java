package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.DanceHall;
import com.techelevator.model.Event;
import com.techelevator.model.Review;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //create and add a new review for an event - registered users and admin
    @Override
    public Review createReview(Review newReview) {

        Review review = null;
        String sql = "INSERT INTO reviews (event_id, review_text, event_rating) " +
                "VALUES (?, ?, ?) RETURNING review_id; ";

        try {
            int newReviewId = jdbcTemplate.queryForObject(sql, int.class,
                    newReview.getEventId(),
                    newReview.getReviewText(),
                    newReview.getEventRating());

            review = getReviewById(newReviewId);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    //retrieve a list of all reviews - everyone
    @Override
    public List<Review> getReviews() {

        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews ";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                reviews.add(mapRowToReview(results));
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return reviews;
    }

    //retrieve an existing review by review id - everyone
    @Override
    public Review getReviewById(int id) {

        Review review = null;
        String sql = "SELECT * FROM reviews WHERE review_id = ? ;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                review = mapRowToReview(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    // will update in next stage
    //retrieve a list of reviews for an event by event id - everyone
    @Override
    public List<Review> getReviewsByEventId(int eventId) {

        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE event_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, eventId);
            while (results.next()) {
                reviews.add(mapRowToReview(results));
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return reviews;
    }

    //update an existing review by review id - registered users and admin
    @Override
    public Review updateReview(Review updatedReview) {

        String sql = "UPDATE reviews " +
                " SET review_text=?, event_rating=? " +
                "WHERE review_id=?;";

        try {
            int rowsAffected =
                    jdbcTemplate.update(sql,
                            updatedReview.getReviewText(),
                            updatedReview.getEventRating(),
                            updatedReview.getReviewId());
            if(rowsAffected == 0){
                throw new DaoException( "Error updating review");
            }

        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getReviewById(updatedReview.getReviewId());
    }

    //delete an existing review by review id - admin only
    @Override
    public void deleteReviewById(int id) {

        String sql1 = "DELETE FROM reviews WHERE review_id= ? ;";

        try {
            int rowsAffected =
                    jdbcTemplate.update(sql1,id);

            if (rowsAffected ==0)
                throw new DaoException("Error deleting review" + id);

        }
        catch (DataIntegrityViolationException e){
            throw new DaoException("Error deleting review " + id, e);
        }
        catch (DataAccessException e){
            throw new DaoException("Unable to delete review" + id, e);
        }
    }

    private Review mapRowToReview(SqlRowSet rowSet){
        Review review = new Review();
        review.setReviewId(rowSet.getInt("review_id"));
        review.setEventId(rowSet.getInt("event_id"));
        review.setReviewText(rowSet.getString("review_text"));
        review.setEventRating(rowSet.getInt("event_rating"));
        return review;
    }
}

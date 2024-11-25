package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.DanceHall;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDanceHallDao implements DanceHallDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDanceHallDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //create and add a new dance hall - admin only
    @Override
    public DanceHall createDanceHall(DanceHall newDanceHall) {

        DanceHall danceHall = null;
        String sql = "INSERT INTO dance_halls (dance_hall_name, street_address, city, state, zipcode, user_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING dance_hall_id; ";

        try {
            int newDanceHallId = jdbcTemplate.queryForObject(sql, int.class,
                    newDanceHall.getName(),
                    newDanceHall.getStreetAddress(),
                    newDanceHall.getCity(),
                    newDanceHall.getState(),
                    newDanceHall.getZipCode(),
                    newDanceHall.getUserId());

            danceHall = getDanceHallById(newDanceHallId);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return danceHall;
    }

    //retrieve a list of all dance halls - admin only
    @Override
    public List<DanceHall> getDanceHalls() {

        List<DanceHall> danceHalls = new ArrayList<>();
        String sql = "SELECT * FROM dance_halls ORDER BY dance_hall_id";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                danceHalls.add(mapRowToDanceHall(results));}
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return danceHalls;
    }

    //retrieve information on an existing dance hall by dance hall id - admin only
    @Override
    public DanceHall getDanceHallById(int id) {

        DanceHall danceHall = null;
        String sql = "SELECT * FROM dance_halls WHERE dance_hall_id = ? ;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                danceHall = mapRowToDanceHall(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return danceHall;
    }

    //update an existing dance hall by dance hall id - admin only
    @Override
    public DanceHall updateDanceHall(DanceHall updatedDanceHall) {

        String sql = "UPDATE dance_halls " +
                " SET dance_hall_name=?, street_address=?, city=?, state=?, zipcode=?, user_id=? " +
                "WHERE dance_hall_id=?;";

        try {
            int rowsAffected =
                    jdbcTemplate.update(sql,
                            updatedDanceHall.getName(),
                            updatedDanceHall.getStreetAddress(),
                            updatedDanceHall.getCity(),
                            updatedDanceHall.getState(),
                            updatedDanceHall.getZipCode(),
                            updatedDanceHall.getUserId(),
                            updatedDanceHall.getDanceHallId());
            if(rowsAffected == 0){
                throw new DaoException( "Error updating Dance Hall");
            }

        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getDanceHallById(updatedDanceHall.getDanceHallId());
    }

    //delete an existing dance hall by dance hall id - admin only
    @Override
    public int deleteDanceHallById(int id) {

        String sql1 = "DELETE FROM events WHERE dance_hall_id= ? ;";
        String sql2 = "DELETE FROM dance_halls WHERE dance_hall_id= ? ;";

        try {
            jdbcTemplate.update(sql1,id);
            int rowsAffected = jdbcTemplate.update(sql2,id);

            return rowsAffected;
        }
        catch (DataIntegrityViolationException e){
            throw new DaoException("Error deleting product " + id, e);
        }
        catch (DataAccessException e){
            throw new DaoException("Unable to delete product" + id, e);
        }
    }

    private DanceHall mapRowToDanceHall(SqlRowSet rowSet){
        DanceHall danceHall = new DanceHall();
        danceHall.setDanceHallId(rowSet.getInt("dance_hall_id"));
        danceHall.setName(rowSet.getString("dance_hall_name"));
        danceHall.setStreetAddress(rowSet.getString("street_address"));
        danceHall.setCity(rowSet.getString("city"));
        danceHall.setState(rowSet.getString("state"));
        danceHall.setZipCode(rowSet.getString("zipcode"));
        return  danceHall;
    }

}

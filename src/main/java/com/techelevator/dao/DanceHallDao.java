package com.techelevator.dao;

import com.techelevator.model.DanceHall;

import java.util.List;

public interface DanceHallDao {

        //create and add a new dance hall - admin only
        DanceHall createDanceHall(DanceHall danceHall);

        //retrieve a list of all dance halls - everyone
        List<DanceHall> getDanceHalls();

        //retrieve information on an existing dance hall by dance hall id - everyone
        DanceHall getDanceHallById(int id);

        //update an existing dance hall by dance hall id - admin only
        DanceHall updateDanceHall(DanceHall danceHall);

        //delete an existing dance hall by dance hall id - admin only
        int deleteDanceHallById(int id);

        //will update in next stage
        // check for manager assigned to that specific dance hall
        //public boolean isManagerOfDanceHall(int danceHallId, int manager_id);
}

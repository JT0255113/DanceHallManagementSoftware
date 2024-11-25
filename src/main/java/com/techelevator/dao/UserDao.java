package com.techelevator.dao;

import com.techelevator.model.User;
import java.util.List;

public interface UserDao {

        //create and add a new registered user - admin only
        User createUser(User newUser);

        //retrieve a list of all registered users - admin only
        List<User> getUsers();

        //retrieve information on a registered user by user Id -admin only
        User getUserById(int userId);

        //will update in next stage
        //retrieve a registered user by user name -admin only
        User getUserByUsername(String username);

        //update an existing registered user by user id - admin only
        User updateUser(User UpdateUser);

        //delete an existing registered user by id - admin only
        void deleteUserById(int id);

}

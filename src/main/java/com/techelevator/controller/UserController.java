package com.techelevator.controller;


import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.DanceHall;
import com.techelevator.model.User;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao){
        this.userDao = userDao;
    }

    //CREATE USER endpoint in AuthenticationController

    //retrieve a list of all registered users - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<User> list() {
        return userDao.getUsers();
    }

    //retrieve information on a registered user by user Id -admin only
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable int id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        else {
            return user;
        }
    }

    //will update in next stage
    //retrieve a registered user by user name -admin only
    //User getUserByUsername(String username);

    //update an existing registered user by user id - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public User update(@Valid @RequestBody User updatedUser, @PathVariable int id) {

        //check that the user exists
        User existingUser = userDao.getUserById(id);
        if (existingUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }

        // The id on the path takes precedence over the id in the request body, if any
        updatedUser.setId(id);
        try {
            return userDao.updateUser(updatedUser);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error updating user");
        } 
    }

    //delete an existing registered user by id - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        if (userDao.getUserById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
        userDao.deleteUserById(id);
    }

}

package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DanceHall {

    //dance hall id
    private int danceHallId;

    //dance hall name
    @NotBlank(message = "The field `Dance Hall Name` should not be blank.")
    private String name;
    //dance hall street address
    @NotBlank(message = "The field `Street Address` should not be blank.")
    private String streetAddress;
    //dance hall city
    @NotBlank(message = "The field `City` should not be blank.")
    private String city;
    //dance hall state
    @NotBlank(message = "The field `State` should not be blank.")
    private String state;
    //dance hall zip code
    @NotBlank(message = "The field `Zip Code` should not be blank.")
    private String zipCode;
    // user id of the manager or admin
    @NotNull(message = "The field `User Id` should not be null.")
    private Integer userId;


    public DanceHall(){}

    //dance hall constructor
    public DanceHall(int danceHallId, String name, String streetAddress, String city, String state, String zipCode) {
        this.danceHallId = danceHallId;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    //overloaded dance hall constructor
    public DanceHall(int danceHallId, String name, String streetAddress, String city, String state, String zipCode, Integer userId) {
        this.danceHallId = danceHallId;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.userId= userId;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public int getDanceHallId(){
        return danceHallId;
    }

    public void setDanceHallId(int danceHallId) {
        this.danceHallId = danceHallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }



    @Override
    public String toString() {
        return "DanceHall{" +
                "danceHallId=" + danceHallId +
                ", name='" + name + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", userId=" + userId +
                '}';
    }


}

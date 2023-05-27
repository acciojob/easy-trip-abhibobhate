package com.driver.controllers;

public class PassengerHasAlreadyBookedFlight extends RuntimeException{
    PassengerHasAlreadyBookedFlight(String s){
        super(s);
    }
}

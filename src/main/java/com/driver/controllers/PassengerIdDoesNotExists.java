package com.driver.controllers;

public class PassengerIdDoesNotExists extends RuntimeException {
    public PassengerIdDoesNotExists(String s) {
        super(s);
    }
}

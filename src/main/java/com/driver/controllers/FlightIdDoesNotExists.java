package com.driver.controllers;

public class FlightIdDoesNotExists extends RuntimeException{
    FlightIdDoesNotExists(String s){
        super(s);
    }
}

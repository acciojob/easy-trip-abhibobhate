package com.driver.controllers;

public class FlightIsFull extends RuntimeException{
    FlightIsFull(String s){
        super(s);
    }
}

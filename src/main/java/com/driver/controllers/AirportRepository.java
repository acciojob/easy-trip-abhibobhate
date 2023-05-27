package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class AirportRepository {
    Map<String,Airport> airports = new HashMap<>();
    Map<Integer,Flight> flights = new HashMap<>();
    Map<Integer,Passenger> passengers = new HashMap<>();
    Map<Integer,Flight> bookingByPassenger = new HashMap<>();
    Map<Integer,Integer> numberOfBookings = new HashMap<>();
    Map<Integer,Integer> flightTotalPrice = new HashMap<>();
    Map<Integer,List<Integer>> bookingByFlight = new HashMap<>();

    public void add(Airport airport) {
        airports.put(airport.getAirportName(),airport);
    }
    public void add(Flight flight) {
        flights.put(flight.getFlightId(),flight);
    }
    public void add(Passenger passenger) {
        passengers.put(passenger.getPassengerId(),passenger);
    }

    public List<Airport> getAirportList() {
        return new ArrayList<>(airports.values());
    }


    public Flight getFlightById(Integer flightId) {
        if(!flights.containsKey(flightId)){
            return null;
        }
        return flights.get(flightId);
    }

    public Passenger getPassengerById(Integer passengerId) {
        if(!passengers.containsKey(passengerId)){
            return null;
        }
        return passengers.get(passengerId);
    }

    public boolean checkPassengerBookTicket(Integer passengerId) {
        return bookingByPassenger.containsKey(passengerId);
    }

    public void bookATicket(Integer flightId, Integer passengerId) {
        bookingByPassenger.put(passengerId,flights.get(flightId));
        if(bookingByFlight.get(flightId) == null){
            bookingByFlight.put(flightId,new ArrayList<>());
        }
        flightTotalPrice.put(flightId,flightTotalPrice.getOrDefault(flightId,0)+ addPrice(flightId));

        bookingByFlight.get(flightId).add(passengerId);

        numberOfBookings.put(passengerId,numberOfBookings.getOrDefault(passengerId,0)+1);
    }

    private Integer addPrice(Integer flightId) {
        return 3000 + bookingByFlight.get(flightId).size()*50;
    }

    public boolean checkCapacityOfFlight(Integer flightId) {
        return flights.get(flightId).getMaxCapacity() == bookingByFlight.get(flightId).size();
    }

    public boolean checkPassengerBookTicketWithFlight(Integer flightId, Integer passengerId) {
        if(bookingByPassenger.containsKey(passengerId)){
            return true;
        }
        return bookingByPassenger.get(passengerId).getFlightId() == flightId;
    }

    public void cancelATicket(Integer flightId, Integer passengerId) {
        bookingByPassenger.remove(passengerId);


        List<Integer> list = bookingByFlight.get(flightId);

        for(int i=0;i<list.size();i++) {
            if(list.get(i) == passengerId){
                list.remove(i);
                break;
            }
        }

        if(flightTotalPrice.get(flightId) == 3000){
            flightTotalPrice.remove(flightId);
        }else {
            flightTotalPrice.put(flightId,flightTotalPrice.get(flightId) - addPrice(flightId));
        }

        if(!numberOfBookings.containsKey(passengerId)){
            return;
        }
        if(numberOfBookings.get(passengerId) == 1){
            numberOfBookings.remove(passengerId);
        }else{
            numberOfBookings.put(passengerId,numberOfBookings.get(passengerId)-1);
        }
    }


    public List<Integer> getPassengersList(Integer flightId) {
        return bookingByFlight.get(flightId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return numberOfBookings.getOrDefault(passengerId,0);
    }

    public int calculateFlightFare(Integer flightId) {
        if(!flightTotalPrice.containsKey(flightId)){
            return 0;
        }
        return flightTotalPrice.get(flightId);
    }
    public int calculateCurrentFlightFare(Integer flightId) {
        if(!flightTotalPrice.containsKey(flightId)){
            return 0;
        }
        return addPrice(flightId);
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights.values());
    }

    public Airport getAirportByName(String airportName) {
        if(!airports.containsKey(airportName)){
            return null;
        }
        return airports.get(airportName);
    }
}

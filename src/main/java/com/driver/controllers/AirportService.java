//package com.driver.controllers;
//
//import com.driver.model.Airport;
//import com.driver.model.City;
//import com.driver.model.Flight;
//import com.driver.model.Passenger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//@Service
//public class AirportService {
//    @Autowired
//    AirportRepository airportRepository;
//    public void addAirport(Airport airport) {
//        airportRepository.add(airport);
//    }
//    public void addFlight(Flight flight) {
//        airportRepository.add(flight);
//    }
//    public void addPassenger(Passenger passenger) {
//        airportRepository.add(passenger);
//    }
//
//    public String getLargestAirportName() {
//        List<Airport> airportList = airportRepository.getAirportList();
//        if(airportList.size()==0){
//            throw new AirportListIsEmpty("Airport List is Empty");
//        }
//        String airportName = airportList.get(0).getAirportName();
//        int max = airportList.get(0).getNoOfTerminals();
//
//        for(Airport airport : airportList){
//            String airportName1 = airport.getAirportName();
//            int currTerminal = airport.getNoOfTerminals();
//
//            if(currTerminal > max || (currTerminal == max && airportName1.compareTo(airportName) < 0)){
//                airportName = airportName1;
//                max = currTerminal;
//            }
//        }
//
//        return airportName;
//
//    }
//
//
//    public void bookATicket(Integer flightId, Integer passengerId) {
//        Flight flight = airportRepository.getFlightById(flightId);
//        if(flight == null){
//            throw new FlightIdDoesNotExists("Flight Id Does Not Exists");
//        }
//
//        Passenger passenger = airportRepository.getPassengerById(passengerId);
//        if(passenger == null){
//            throw new PassengerIdDoesNotExists("Passenger Id Does Not Exists");
//        }
//
//        if(airportRepository.checkPassengerBookTicket(passengerId)){
//            throw new PassengerHasAlreadyBookedFlight("Passenger has already Booked a Flight");
//        }
//
//        if(airportRepository.checkCapacityOfFlight(flightId)){
//            throw new FlightIsFull("Flight is full");
//        }
//
//
//        airportRepository.bookATicket(flightId,passengerId);
//
//
//    }
//
//    public void cancelATicket(Integer flightId, Integer passengerId) {
//        Flight flight = airportRepository.getFlightById(flightId);
//        if(flight == null){
//            throw new FlightIdDoesNotExists("Flight Id Does Not Exists");
//        }
//
//        Passenger passenger = airportRepository.getPassengerById(passengerId);
//        if(passenger == null){
//            throw new PassengerIdDoesNotExists("Passenger Id Does Not Exists");
//        }
//
//        if(!airportRepository.checkPassengerBookTicketWithFlight(flightId,passengerId)){
//            throw new PassengerHasAlreadyBookedFlight("Passenger has not Booked a Flight");
//        }
//        airportRepository.cancelATicket(flightId,passengerId);
//    }
//
//    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
//        Passenger passenger = airportRepository.getPassengerById(passengerId);
//        if(passenger == null){
//            throw new PassengerIdDoesNotExists("Passenger Id Does Not Exists");
//        }
//        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
//    }
//
//    public String getAirportNameFromFlightId(Integer flightId) {
//        Flight flight = airportRepository.getFlightById(flightId);
//        if(flight == null){
//            throw new FlightIdDoesNotExists("Flight Id Does Not Exists");
//        }
//        return flight.getFromCity().name();
//    }
//
//    public int calculateFlightFare(Integer flightId) {
//        Flight flight = airportRepository.getFlightById(flightId);
//        if(flight == null){
//            throw new FlightIdDoesNotExists("Flight Id Does Not Exists");
//        }
//        return airportRepository.calculateFlightFare(flightId);
//    }
//    public int calculateCurrentFlightFare(Integer flightId) {
//        Flight flight = airportRepository.getFlightById(flightId);
//        if(flight == null){
//            throw new FlightIdDoesNotExists("Flight Id Does Not Exists");
//        }
//        return airportRepository.calculateCurrentFlightFare(flightId);
//    }
//
//    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
//        List<Flight> flights = airportRepository.getAllFlights();
//        double min = Double.MAX_VALUE;
//        for(Flight flight : flights){
//            if(flight.getFromCity().name().equals(fromCity) && flight.getToCity().name().equals(toCity)){
//                if(flight.getDuration() < min){
//                    min = flight.getDuration();
//                }
//            }
//        }
//        return min;
//    }
//
//    public int getNumberOfPeopleOn(Date date, String airportName) {
//        Airport airport = airportRepository.getAirportByName(airportName);
//        if(airport == null)return 0;
//
//        int count = 0;
//        City city = airport.getCity();
//        for(Flight flight : airportRepository.getAllFlights()){
//            if(date.equals(flight.getFlightDate())){
//                if(city.equals(flight.getFromCity()) || city.equals(flight.getToCity())){
//                    count += airportRepository.getPassengersList(flight.getFlightId()).size();
//                }
//            }
//        }
//        return count;
//    }
//}


//new

package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.Date;

public class AirportService {
    AirportRepository airportRepository= new AirportRepository();

    public void addAirport(Airport airport) {
        airportRepository.addAirport(airport);
    }

    public String getLargestAirportName() {
        return airportRepository.getLargestAirportName();
    }

    public void aaddFlight(Flight flight) {
        airportRepository.addFlight(flight);
    }

    public void addPassenger(Passenger passenger) {
        airportRepository.addPassenger(passenger);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        return  airportRepository.bookATicket(flightId,passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return airportRepository.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public int calculateFare(Integer flightId) {
        return airportRepository.calculateFare(flightId);
    }

    public double getShortestTime(City fromCity, City toCity) {
        return airportRepository.getShortestTime(fromCity,toCity);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

    public String getAirportName(Integer flightId) {
        return airportRepository.getAirportNmae(flightId);
    }

    public int getNumberOfPeople(Date date, String airportName) {
        return airportRepository.getNumberOfPeople(date, airportName);
    }
}
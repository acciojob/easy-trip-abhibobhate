//package com.driver.controllers;
//
//import com.driver.model.Airport;
//import com.driver.model.Flight;
//import com.driver.model.Passenger;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//@Repository
//public class AirportRepository {
//    Map<String,Airport> airports = new HashMap<>();
//    Map<Integer,Flight> flights = new HashMap<>();
//    Map<Integer,Passenger> passengers = new HashMap<>();
//    Map<Integer,Flight> bookingByPassenger = new HashMap<>();
//    Map<Integer,Integer> numberOfBookings = new HashMap<>();
//    Map<Integer,Integer> flightTotalPrice = new HashMap<>();
//    Map<Integer,List<Integer>> bookingByFlight = new HashMap<>();
//
//    public void add(Airport airport) {
//        airports.put(airport.getAirportName(),airport);
//    }
//    public void add(Flight flight) {
//        flights.put(flight.getFlightId(),flight);
//    }
//    public void add(Passenger passenger) {
//        passengers.put(passenger.getPassengerId(),passenger);
//    }
//
//    public List<Airport> getAirportList() {
//        return new ArrayList<>(airports.values());
//    }
//
//
//    public Flight getFlightById(Integer flightId) {
//        if(!flights.containsKey(flightId)){
//            return null;
//        }
//        return flights.get(flightId);
//    }
//
//    public Passenger getPassengerById(Integer passengerId) {
//        if(!passengers.containsKey(passengerId)){
//            return null;
//        }
//        return passengers.get(passengerId);
//    }
//
//    public boolean checkPassengerBookTicket(Integer passengerId) {
//        return bookingByPassenger.containsKey(passengerId);
//    }
//
//    public void bookATicket(Integer flightId, Integer passengerId) {
//        bookingByPassenger.put(passengerId,flights.get(flightId));
//        if(bookingByFlight.get(flightId) == null){
//            bookingByFlight.put(flightId,new ArrayList<>());
//        }
//        flightTotalPrice.put(flightId,flightTotalPrice.getOrDefault(flightId,0)+ addPrice(flightId));
//
//        bookingByFlight.get(flightId).add(passengerId);
//
//        numberOfBookings.put(passengerId,numberOfBookings.getOrDefault(passengerId,0)+1);
//    }
//
//    private Integer addPrice(Integer flightId) {
//        return 3000 + bookingByFlight.get(flightId).size()*50;
//    }
//
//    public boolean checkCapacityOfFlight(Integer flightId) {
//        return flights.get(flightId).getMaxCapacity() == bookingByFlight.get(flightId).size();
//    }
//
//    public boolean checkPassengerBookTicketWithFlight(Integer flightId, Integer passengerId) {
//        if(bookingByPassenger.containsKey(passengerId)){
//            return true;
//        }
//        return bookingByPassenger.get(passengerId).getFlightId() == flightId;
//    }
//
//    public void cancelATicket(Integer flightId, Integer passengerId) {
//        bookingByPassenger.remove(passengerId);
//
//
//        List<Integer> list = bookingByFlight.get(flightId);
//
//        for(int i=0;i<list.size();i++) {
//            if(list.get(i) == passengerId){
//                list.remove(i);
//                break;
//            }
//        }
//
//        if(flightTotalPrice.get(flightId) == 3000){
//            flightTotalPrice.remove(flightId);
//        }else {
//            flightTotalPrice.put(flightId,flightTotalPrice.get(flightId) - addPrice(flightId));
//        }
//
//        if(!numberOfBookings.containsKey(passengerId)){
//            return;
//        }
//        if(numberOfBookings.get(passengerId) == 1){
//            numberOfBookings.remove(passengerId);
//        }else{
//            numberOfBookings.put(passengerId,numberOfBookings.get(passengerId)-1);
//        }
//    }
//
//
//    public List<Integer> getPassengersList(Integer flightId) {
//        return bookingByFlight.get(flightId);
//    }
//
//    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
//        return numberOfBookings.getOrDefault(passengerId,0);
//    }
//
//    public int calculateFlightFare(Integer flightId) {
//        if(!flightTotalPrice.containsKey(flightId)){
//            return 0;
//        }
//        return flightTotalPrice.get(flightId);
//    }
//    public int calculateCurrentFlightFare(Integer flightId) {
//        if(!flightTotalPrice.containsKey(flightId)){
//            return 0;
//        }
//        return addPrice(flightId);
//    }
//
//    public List<Flight> getAllFlights() {
//        return new ArrayList<>(flights.values());
//    }
//
//    public Airport getAirportByName(String airportName) {
//        if(!airports.containsKey(airportName)){
//            return null;
//        }
//        return airports.get(airportName);
//    }
//}


//new

package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.*;

public class AirportRepository {
    private TreeMap<String,Airport> airportMap= new TreeMap<>();
    private HashMap<Integer,Flight> flightMap= new HashMap<>();
    private HashMap<Integer,Passenger> passengerMap= new HashMap<>();
    private HashMap<Integer,Set<Integer>> flightPassMap= new HashMap<>();
    private HashMap<Integer,Integer> revenueMap= new HashMap<>();
    private HashMap<Integer,Integer> paymentMap= new HashMap<>();

    public void addAirport(Airport airport) {

        airportMap.put(airport.getAirportName(),airport);
    }

    public String getLargestAirportName() {
        String answer="";
        int ans=0;
        for(String name:airportMap.keySet()){
            int co=airportMap.get(name).getNoOfTerminals();
            if(co>ans){
                ans=co;
                answer=name;
            }
        }
        return answer;
    }

    public void addFlight(Flight flight) {
        flightMap.put(flight.getFlightId(),flight);
    }

    public void addPassenger(Passenger passenger) {
        passengerMap.put(passenger.getPassengerId(),passenger);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        Flight flight=flightMap.get(flightId);
        int maxcapacity=flight.getMaxCapacity();
        Set<Integer> list= new HashSet<>();
        if(flightPassMap.containsKey(flightId)){
            list=flightPassMap.get(flightId);
        }
        int capacity=list.size();
        if(capacity==maxcapacity) return "FAILURE";
        else if(list.contains(passengerId)) return "FAILURE";
        int fare=calculateFare(flightId);
        paymentMap.put(passengerId,fare);
        fare+=revenueMap.getOrDefault(flightId,0);
        revenueMap.put(flightId,fare);
        list.add(passengerId);
        flightPassMap.put(flightId,list);
        return "SUCCESS";
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        Set<Integer> list= flightPassMap.get(flightId);
        if(list.contains(passengerId)){
            list.remove(passengerId);
            int fare=paymentMap.getOrDefault(passengerId,0);
            paymentMap.remove(passengerId);
            int revenue=revenueMap.getOrDefault(flightId,0);
            revenueMap.put(flightId,revenue-fare);
            return "SUCCESS";
        }
        return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int count=0;
        for(Integer flightId:flightPassMap.keySet()){
            Set<Integer> list=flightPassMap.get(flightId);
            if(list.contains(passengerId)){
                count++;
            }
        }
        return count;
    }

    public int calculateFare(Integer flightId) {
        int fare=3000;
        int alreadyBooked=0;
        if(flightPassMap.containsKey(flightId))
            alreadyBooked=flightPassMap.get(flightId).size();
        return (fare+(alreadyBooked*50));
    }

    public double getShortestTime(City fromCity, City toCity) {
        double duration=Integer.MAX_VALUE;
        for (Flight flight :flightMap.values()){
            if(fromCity.equals(flight.getFromCity()) && toCity.equals(flight.getToCity())){
                if(duration>flight.getDuration()){
                    duration=flight.getDuration();
                }
            }
        }
        return duration==Integer.MAX_VALUE?-1:duration;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        Integer revenue= revenueMap.getOrDefault(flightId,0);
        return revenue;
    }

    public String getAirportNmae(Integer flightId) {
        if(!flightMap.containsKey(flightId)) return null;
        Flight flight= flightMap.get(flightId);
        City city=flight.getFromCity();
        for (String airportname:airportMap.keySet()){
            Airport airport=airportMap.get(airportname);
            if(city.equals(airport.getCity())){
                return airportname;
            }
        }
        return null;
    }

    public int getNumberOfPeople(Date date, String airportName) {
        Airport airport=airportMap.get(airportName);
        int count=0;
        if(airport!=null){
            City city=airport.getCity();
            for(Flight flight : flightMap.values()){
                if(date.equals(flight.getFlightDate())){
                    if(city.equals(flight.getToCity()) || city.equals(flight.getFromCity())){
                        Integer flightId=flight.getFlightId();
                        Set<Integer> list=flightPassMap.get(flightId);
                        if(list!=null){
                            count+= list.size();
                        }
                    }
                }
            }}
        return count;
    }
}
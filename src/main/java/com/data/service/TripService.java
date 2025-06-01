package com.data.service;

import com.data.model.Trip;
import java.util.List;

public interface TripService {
    List<Trip> getAllTrip(int page, int size);
    List<Trip> searchTrips(String departure, String destination, int page, int size);
    List<Trip> getAllTrips();
    long countAllTrips();
    long countTripsByPoint(String departure, String destination);
    boolean addTrip(Trip trip);
    boolean updateTrip(Trip trip);
    boolean deleteTrip(int id);
    Trip getTripById(int id);
    List<Trip> searchTripsByDeparture(String departure, int page, int size);
    long countTripsByDeparture(String departure);
    List<Trip> searchTripsByDestination(String destination, int page, int size);
    long countTripsByDestination(String destination);
    List<Trip> searchTripsByDepartureOrDestination(String departure, String destination, int page, int size);
    long countTripsByDepartureOrDestination(String departure, String destination);}
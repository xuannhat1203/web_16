package com.data.repository;
import com.data.model.Trip;

import java.util.List;

public interface TripRepository {
    List<Trip> searchTrips(String departure, String destination, int page, int size);
    List<Trip> getAllTrips();
    long countTripsByPoint(String departure, String destination);
    boolean addTrip(Trip trip);
    boolean updateTrip(Trip trip);
    boolean deleteTrip(int id);
    Trip getTripById(int id);
    List<Trip> getAllTrip(int page, int size);
    long countAllTrips();
    List<Trip> searchTripsByDeparture(String departure, int page, int size);
    long countTripsByDeparture(String departure);
    List<Trip> searchTripsByDestination(String destination, int page, int size);
    long countTripsByDestination(String destination);
    List<Trip> searchTripsByDepartureOrDestination(String departure, String destination, int page, int size);
    long countTripsByDepartureOrDestination(String departure, String destination);
}
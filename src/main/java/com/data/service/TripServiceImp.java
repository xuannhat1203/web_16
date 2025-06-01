package com.data.service;

import com.data.model.Trip;
import com.data.repository.TripRepository;
import com.data.repository.TripRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImp implements TripService {
    private final TripRepository tripRepository;
    public TripServiceImp() {
        this.tripRepository = new TripRepositoryImp();
    }
    @Override
    public List<Trip> getAllTrip(int page, int size) {
        return tripRepository.getAllTrip(page, size);
    }
    @Override
    public List<Trip> searchTrips(String departure, String destination, int page, int size) {
        return tripRepository.searchTrips(departure, destination, page, size);
    }
    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.getAllTrips();
    }
    @Override
    public long countAllTrips() {
        return tripRepository.countAllTrips();
    }
    @Override
    public long countTripsByPoint(String departure, String destination) {
        return tripRepository.countTripsByPoint(departure, destination);
    }
    @Override
    public boolean addTrip(Trip trip) {
        return tripRepository.addTrip(trip);
    }
    @Override
    public boolean updateTrip(Trip trip) {
        return tripRepository.updateTrip(trip);
    }
    @Override
    public boolean deleteTrip(int id) {
        return tripRepository.deleteTrip(id);
    }
    @Override
    public Trip getTripById(int id) {
        return tripRepository.getTripById(id);
    }
    @Override
    public List<Trip> searchTripsByDeparture(String departure, int page, int size) {
        return tripRepository.searchTripsByDeparture(departure, page, size);
    }
    @Override
    public long countTripsByDeparture(String departure) {
        return tripRepository.countTripsByDeparture(departure);
    }
    @Override
    public List<Trip> searchTripsByDestination(String destination, int page, int size) {
        return tripRepository.searchTripsByDestination(destination, page, size);
    }
    @Override
    public long countTripsByDestination(String destination) {
        return tripRepository.countTripsByDestination(destination);
    }
    @Override
    public List<Trip> searchTripsByDepartureOrDestination(String departure, String destination, int page, int size) {
        return tripRepository.searchTripsByDepartureOrDestination(departure, destination, page, size);
    }
    @Override
    public long countTripsByDepartureOrDestination(String departure, String destination) {
        return tripRepository.countTripsByDepartureOrDestination(departure, destination);
    }
}
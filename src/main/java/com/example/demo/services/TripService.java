package com.example.demo.services;


import com.example.demo.models.Trip;
import com.example.demo.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    // Add a new trip
    public Trip addTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    // Get all trips
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    // Get a trip by ID
    public Trip getTripById(Long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        return optionalTrip.orElse(null);
    }

    // Update a trip
    public Trip updateTrip(Long id, Trip updatedTrip) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        if (optionalTrip.isPresent()) {
            Trip existingTrip = optionalTrip.get();
            existingTrip.setTripDate(updatedTrip.getTripDate());
            existingTrip.setOrigin(updatedTrip.getOrigin());
            existingTrip.setDestination(updatedTrip.getDestination());
            existingTrip.setTripCost(updatedTrip.getTripCost());
            existingTrip.setCaptain(updatedTrip.getCaptain());
            existingTrip.setCustomer(updatedTrip.getCustomer());
            return tripRepository.save(existingTrip);
        } else {
            return null;
        }
    }

    // Delete a trip
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    // Find trips within a date range
    public List<Trip> findTripsWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return tripRepository.findByTripDateBetween(startDate, endDate);
    }

    // Find trips by captain ID
    public List<Trip> findTripsByCaptainId(Long captainId) {
        return tripRepository.findByCaptainId(captainId);
    }
}

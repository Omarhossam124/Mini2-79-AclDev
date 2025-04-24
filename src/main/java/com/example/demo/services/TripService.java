package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Captain;
import com.example.demo.models.Trip;
import com.example.demo.repositories.CaptainRepository;
import com.example.demo.repositories.TripRepository;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final CaptainRepository captainRepository;
    @Autowired
    public TripService(TripRepository tripRepository,CaptainRepository captainRepository) {
        this.tripRepository = tripRepository;
        this.captainRepository = captainRepository;
    }

    // Add a new trip
    public Trip addTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    // Get all trips
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    // Get trip by ID
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    // Update trip
    public Trip updateTrip(Long id, Trip trip) {
        if (tripRepository.existsById(id)) {
            trip.setId(id);
            return tripRepository.save(trip);
        }
        return null;
    }

    // Delete trip
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }
    

    // Find trips within date range
    public List<Trip> findTripsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return tripRepository.findByTripDateBetween(startDate, endDate);
    }

    // Find trips by captain ID
    public List<Trip> findTripsByCaptainId(Long captainId) {
        return tripRepository.findByCaptainId(captainId);
    }

}
package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Trip;
import com.example.demo.services.TripService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/addTrip")
    public ResponseEntity<Trip> addTrip(@RequestBody Trip trip) {
        Trip newTrip = tripService.addTrip(trip);
        return new ResponseEntity<>(newTrip, HttpStatus.OK);
    }

    @GetMapping("/allTrips")
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        return trip != null ? 
            new ResponseEntity<>(trip, HttpStatus.OK) : 
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
        Trip updatedTrip = tripService.updateTrip(id, trip);
        return updatedTrip != null ? 
            new ResponseEntity<>(updatedTrip, HttpStatus.OK) : 
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return new ResponseEntity<>("Trip deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/findByDateRange")
    public ResponseEntity<List<Trip>> findTripsWithinDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Trip> trips = tripService.findTripsByDateRange(startDate, endDate);
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/findByCaptainId")
    public ResponseEntity<List<Trip>> findTripsByCaptainId(@RequestParam Long captainId) {
        List<Trip> trips = tripService.findTripsByCaptainId(captainId);
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }
}
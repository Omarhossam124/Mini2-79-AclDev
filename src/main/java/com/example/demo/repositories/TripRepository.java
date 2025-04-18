package com.example.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Trip;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    // Retrieve all trips that occurred between two specific dates
    List<Trip> findByTripDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Retrieve all trips associated with a specific captain’s ID
    List<Trip> findByCaptainId(Long captainId);
}

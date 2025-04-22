package com.example.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Trip;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    
    // Find trips within a date range
    List<Trip> findByTripDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find trips by captain ID
    List<Trip> findByCaptainId(Long captainId);
    
    // Custom query example (if you need more complex queries)
    @Query("SELECT t FROM Trip t WHERE t.captainId = :captainId AND t.tripDate BETWEEN :startDate AND :endDate")
    List<Trip> findTripsByCaptainAndDateRange(Long captainId, LocalDateTime startDate, LocalDateTime endDate);
}
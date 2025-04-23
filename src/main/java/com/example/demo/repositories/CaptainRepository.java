package com.example.demo.repositories;

import com.example.demo.models.Captain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaptainRepository extends JpaRepository<Captain, Long> {
    
    // Query to find all captains with a rating above a threshold
    List<Captain> findByAvgRatingScoreGreaterThan(Double threshold);

    // Query to locate a captain by license number
    Captain findByLicenseNumber(String licenseNumber);
}

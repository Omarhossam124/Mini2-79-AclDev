package com.example.demo.services;

import com.example.demo.models.Captain;
import com.example.demo.repositories.CaptainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaptainService {
    // Dependency Injection
    private final CaptainRepository captainRepository;

    @Autowired
    public CaptainService(CaptainRepository captainRepository) {
        this.captainRepository = captainRepository;
    }

    // 8.1.2.1 Add Captain
    public Captain addCaptain(Captain captain) {
        return captainRepository.save(captain); // Save the captain to the database
    }

    // 8.1.2.2 Get All Captains
    public List<Captain> getAllCaptains() {
        return captainRepository.findAll(); // Retrieve all captains
    }

    // 8.1.2.3 Get Captain By ID
    public Captain getCaptainById(Long id) {
        return captainRepository.findById(id).orElse(null); // Retrieve captain by ID
    }

    // 8.1.2.4 Filter by Rating
    public List<Captain> getCaptainsByRating(Double ratingThreshold) {
        return captainRepository.findByAvgRatingScoreGreaterThan(ratingThreshold); // Filter captains by rating
    }

    // 8.1.2.5 Filter by License Number
    public Captain getCaptainByLicenseNumber(String licenseNumber) {
        return captainRepository.findByLicenseNumber(licenseNumber); // Retrieve captain by license number
    }
}

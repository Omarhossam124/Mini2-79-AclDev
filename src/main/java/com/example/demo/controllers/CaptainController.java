package com.example.demo.controllers;

import com.example.demo.models.Captain;
import com.example.demo.services.CaptainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/captain")
public class CaptainController {
    private final CaptainService captainService;

    @Autowired
    public CaptainController(CaptainService captainService) {
        this.captainService = captainService;
    }

    // 9.1.2.1 Add Captain
    @PostMapping("/addCaptain")
    public Captain addCaptain(@RequestBody Captain captain) {
        return captainService.addCaptain(captain);
    }

    // 9.1.2.2 Get All Captains
    @GetMapping("/allCaptains")
    public List<Captain> getAllCaptains() {
        return captainService.getAllCaptains();
    }

    // 9.1.2.3 Get Specific Captain
    @GetMapping("/{id}")
    public Captain getCaptainById(@PathVariable Long id) {
        return captainService.getCaptainById(id);
    }

    // 9.1.2.4 Filter Captains By Rating
    @GetMapping("/filterByRating")
    public List<Captain> getCaptainsByRating(@RequestParam Double ratingThreshold) {
        return captainService.getCaptainsByRating(ratingThreshold);
    }

    // 9.1.2.5 Filter Captain By License Number
    @GetMapping("/filterByLicenseNumber")
    public Captain getCaptainByLicenseNumber(@RequestParam String licenseNumber) {
        return captainService.getCaptainByLicenseNumber(licenseNumber);
    }
}

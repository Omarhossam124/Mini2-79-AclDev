package com.example.demo.models;

import java.util.List;


import jakarta.persistence.*;

@Entity
@Table(name = "captains")
public class Captain {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the captain

    private String name;
    private String licenseNumber;
    private Double avgRatingScore;

    @OneToMany(mappedBy = "captain", cascade = CascadeType.ALL)
    private List<Trip> trips; // A captain can have many trips

    // Constructors, Getters, and Setters
    public Captain() {}

    public Captain(String name, String licenseNumber, Double avgRatingScore) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.avgRatingScore = avgRatingScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Double getAvgRatingScore() {
        return avgRatingScore;
    }

    public void setAvgRatingScore(Double avgRatingScore) {
        this.avgRatingScore = avgRatingScore;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}

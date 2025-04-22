package com.example.demo;

import org.springframework.beans.factory.annotation.Value;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.Captain;
import com.example.demo.models.Trip;
import com.example.demo.repositories.TripRepository;
import com.example.demo.services.TripService;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(statements = {
    // Drop tables if they exist — order matters due to FK constraints
    "DROP TABLE IF EXISTS payments;",
    "DROP TABLE IF EXISTS trips;",
    "DROP TABLE IF EXISTS customers;",
    "DROP TABLE IF EXISTS captains;",

    // Create captains table
    "CREATE TABLE captains (" +
        "id SERIAL PRIMARY KEY, " +
        "name VARCHAR(255) NOT NULL, " +
        "license_number VARCHAR(50) NOT NULL UNIQUE, " +
        "avg_rating_score DOUBLE PRECISION" +
        ");",

    // Create customers table
    "CREATE TABLE customers (" +
        "id SERIAL PRIMARY KEY, " +
        "name VARCHAR(255) NOT NULL, " +
        "email VARCHAR(255) NOT NULL UNIQUE, " +
        "phone_number VARCHAR(15) NOT NULL" +
        ");",

    // Create trips table — note FK references captains and customers
    "CREATE TABLE trips (" +
        "id SERIAL PRIMARY KEY, " +
        "trip_date TIMESTAMP NOT NULL, " +
        "origin VARCHAR(255) NOT NULL, " +
        "destination VARCHAR(255) NOT NULL, " +
        "trip_cost DOUBLE PRECISION NOT NULL, " +
        "captain_id INT REFERENCES captains(id), " +
        "customer_id INT REFERENCES customers(id)" +
        ");",

    // Create payments table — FK to trips
    "CREATE TABLE payments (" +
        "id SERIAL PRIMARY KEY, " +
        "amount DOUBLE PRECISION NOT NULL, " +
        "payment_method VARCHAR(50) NOT NULL, " +
        "payment_status BOOLEAN NOT NULL, " +
        "trip_id INT REFERENCES trips(id)" +
        ");",
}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class TripTests {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CaptainRepository captainRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TripService tripService;
    
    @Autowired
    private TripRepository tripRepository;

    private final String BASE_URL_TRIP = "http://localhost:8080/trip";

    @BeforeEach
    public void setup() {
        // Clear only trip-related data
        tripRepository.deleteAll();
    }

    @Test
    public void testControllerGetAllTrips() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL_TRIP + "/allTrips", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testControllerAddTrip() {
        Trip newTrip = new Trip(LocalDateTime.now(), "Origin A", "Destination A", 100.0);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Trip> request = new HttpEntity<>(newTrip, headers);

        ResponseEntity<Trip> response = restTemplate.postForEntity(
            BASE_URL_TRIP + "/addTrip", 
            request, 
            Trip.class
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(newTrip.getOrigin(), response.getBody().getOrigin());
    }

    @Test
    public void testControllerGetTripById() {
        Trip trip = new Trip(LocalDateTime.now(), "Origin B", "Destination B", 200.0);
        trip = tripService.addTrip(trip);
        
        ResponseEntity<Trip> response = restTemplate.getForEntity(
            BASE_URL_TRIP + "/" + trip.getId(), 
            Trip.class
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(trip.getOrigin(), response.getBody().getOrigin());
    }

    @Test
    public void testControllerUpdateTrip() {
        Trip trip = new Trip(LocalDateTime.now(), "Origin C", "Destination C", 300.0);
        trip = tripService.addTrip(trip);

        trip.setDestination("Updated Destination");
        trip.setTripCost(400.0);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Trip> request = new HttpEntity<>(trip, headers);

        ResponseEntity<Trip> response = restTemplate.exchange(
            BASE_URL_TRIP + "/update/" + trip.getId(),
            HttpMethod.PUT,
            request,
            Trip.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Destination", response.getBody().getDestination());
        assertEquals(400.0, response.getBody().getTripCost());
    }

    @Test
    public void testControllerDeleteTrip() {
        Trip trip = new Trip(LocalDateTime.now(), "Origin D", "Destination D", 500.0);
        trip = tripService.addTrip(trip);

        ResponseEntity<String> response = restTemplate.exchange(
            BASE_URL_TRIP + "/delete/" + trip.getId(),
            HttpMethod.DELETE,
            null,
            String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Verify the trip was actually deleted
        assertFalse(tripRepository.existsById(trip.getId()));
    }

    @Test
    public void testControllerDeleteNonExistingTrip() {
        ResponseEntity<String> response = restTemplate.exchange(
            BASE_URL_TRIP + "/delete/" + 999L,
            HttpMethod.DELETE,
            null,
            String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testControllerFindTripsWithinDateRange() {
        LocalDateTime now = LocalDateTime.now();
        Trip trip1 = new Trip(now.minusDays(1), "Origin E", "Destination E", 150.0);
        Trip trip2 = new Trip(now.plusDays(1), "Origin F", "Destination F", 250.0);
        tripService.addTrip(trip1);
        tripService.addTrip(trip2);

        LocalDateTime startDate = now.minusDays(2);
        LocalDateTime endDate = now;

        ResponseEntity<List> response = restTemplate.getForEntity(
            BASE_URL_TRIP + "/findByDateRange?startDate=" + startDate + "&endDate=" + endDate,
            List.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testControllerFindTripsWithinDateRangeNoResults() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.plusDays(7);
        LocalDateTime endDate = now.plusDays(8);

        ResponseEntity<List> response = restTemplate.getForEntity(
            BASE_URL_TRIP + "/findByDateRange?startDate=" + startDate + "&endDate=" + endDate,
            List.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testControllerFindTripsByCaptainId() {
        // First create a captain
        Captain captain = new Captain("Test Captain", "CAP123", 4.5);
        captain = captainRepository.save(captain);
        
        // Create trips with and without this captain
        Trip trip1 = new Trip(LocalDateTime.now(), "Origin G", "Destination G", 100.0);
        trip1.setCaptainId(captain.getId());
        tripService.addTrip(trip1);
        
        Trip trip2 = new Trip(LocalDateTime.now(), "Origin H", "Destination H", 200.0);
        tripService.addTrip(trip2);

        ResponseEntity<List> response = restTemplate.getForEntity(
            BASE_URL_TRIP + "/findByCaptainId?captainId=" + captain.getId(),
            List.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testControllerFindTripsByCaptainIdNotFound() {
        ResponseEntity<List> response = restTemplate.getForEntity(
            BASE_URL_TRIP + "/findByCaptainId?captainId=999",
            List.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testServiceAddTrip() {
        Trip trip = new Trip(LocalDateTime.now(), "Origin I", "Destination I", 350.0);
        Trip savedTrip = tripService.addTrip(trip);
        
        assertNotNull(savedTrip);
        assertNotNull(savedTrip.getId());
        assertEquals("Origin I", savedTrip.getOrigin());
    }

    @Test
    public void testServiceAddNullTrip() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            tripService.addTrip(null);
        });
    }

    @Test
    public void testServiceGetAllTrips() {
        Trip trip1 = new Trip(LocalDateTime.now(), "Origin J", "Destination J", 400.0);
        Trip trip2 = new Trip(LocalDateTime.now(), "Origin K", "Destination K", 500.0);
        tripService.addTrip(trip1);
        tripService.addTrip(trip2);
        
        List<Trip> trips = tripService.getAllTrips();
        assertNotNull(trips);
        assertEquals(2, trips.size());
    }

    @Test
    public void testServiceGetTripById() {
        Trip trip = new Trip(LocalDateTime.now(), "Origin L", "Destination L", 600.0);
        trip = tripService.addTrip(trip);
        
        Trip foundTrip = tripService.getTripById(trip.getId());
        assertNotNull(foundTrip);
        assertEquals(trip.getId(), foundTrip.getId());
    }

    @Test
    public void testServiceFindTripsByDateRange() {
        LocalDateTime now = LocalDateTime.now();
        Trip trip1 = new Trip(now.minusDays(1), "Origin M", "Destination M", 700.0);
        Trip trip2 = new Trip(now.plusDays(1), "Origin N", "Destination N", 800.0);
        tripService.addTrip(trip1);
        tripService.addTrip(trip2);

        List<Trip> trips = tripService.findTripsByDateRange(now.minusDays(2), now);
        assertNotNull(trips);
        assertEquals(1, trips.size());
        assertEquals("Origin M", trips.get(0).getOrigin());
    }

    @Test
    public void testServiceFindTripsByCaptainId() {
        Captain captain = new Captain("Test Captain 2", "CAP456", 4.8);
        captain = captainRepository.save(captain);
        
        Trip trip1 = new Trip(LocalDateTime.now(), "Origin O", "Destination O", 900.0);
        trip1.setCaptainId(captain.getId());
        tripService.addTrip(trip1);
        
        Trip trip2 = new Trip(LocalDateTime.now(), "Origin P", "Destination P", 1000.0);
        tripService.addTrip(trip2);

        List<Trip> trips = tripService.findTripsByCaptainId(captain.getId());
        assertNotNull(trips);
        assertEquals(1, trips.size());
        assertEquals(captain.getId(), trips.get(0).getCaptainId());
    }
}
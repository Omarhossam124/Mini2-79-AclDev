package com.example.demo.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double amount;
    private String paymentMethod;
    private Boolean paymentStatus;
    
    @OneToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    // Default constructor
    public Payment() {
    }
    public Payment(double amount, String paymentMethod, boolean paymentStatus) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    // Partial constructor (without ID)
    public Payment(Double amount, String paymentMethod, Boolean paymentStatus, Trip trip) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.trip = trip;
    }

    // Full constructor
    public Payment(Long id, Double amount, String paymentMethod, Boolean paymentStatus, Trip trip) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.trip = trip;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
               Objects.equals(amount, payment.amount) &&
               Objects.equals(paymentMethod, payment.paymentMethod) &&
               Objects.equals(paymentStatus, payment.paymentStatus) &&
               Objects.equals(trip, payment.trip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, paymentMethod, paymentStatus, trip);
    }

    // toString()
    @Override
    public String toString() {
        return "Payment{" +
               "id=" + id +
               ", amount=" + amount +
               ", paymentMethod='" + paymentMethod + '\'' +
               ", paymentStatus=" + paymentStatus +
               ", trip=" + trip +
               '}';
    }
}
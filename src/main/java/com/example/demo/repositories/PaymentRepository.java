package com.example.demo.repositories;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.models.Payment;

public class PaymentRepository {
    private List<Payment> payments; // List to store payment records

    // Constructor
    public PaymentRepository() {
        this.payments = new ArrayList<>();
    }

    // Method to add a payment
    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    // Finding payments by trip ID
    public List<Payment> findPaymentsByTripId(long tripId) {
        return payments.stream()
                .filter(payment -> payment.getTrip() != null && payment.getTrip().getId() == tripId)
                .collect(Collectors.toList());
    }

    // Finding payments with an amount above a set threshold
    public List<Payment> findPaymentsAboveThreshold(double threshold) {
        return payments.stream()
                .filter(payment -> payment.getAmount() > threshold)
                .collect(Collectors.toList());
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments); // Return a copy of the payments list
    }
    
    public Payment findById(Long id) {
        return payments.stream()
                .filter(payment -> payment.getId().equals(id))
                .findFirst()
                .orElse(null); // Return the payment or null if not found
    }
    
    public void deleteById(Long id) {
        payments.removeIf(payment -> payment.getId().equals(id)); // Remove payment by ID
    }
}

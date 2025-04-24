package com.example.demo.repositories;

import com.example.demo.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find payments by trip ID (required)
    @Query("SELECT p FROM Payment p WHERE p.trip.id = :tripId")
    List<Payment> findPaymentsByTripId(Long tripId);

    // Find payments with amount greater than threshold (required)
    @Query("SELECT p FROM Payment p WHERE p.amount > :threshold")
    List<Payment> findByAmountGreaterThan(Double threshold);
    
    // Additional useful queries
    List<Payment> findByPaymentMethod(String paymentMethod);
    
    List<Payment> findByPaymentStatus(Boolean paymentStatus);
}
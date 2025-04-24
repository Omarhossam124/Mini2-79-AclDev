package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Payment;
import com.example.demo.repositories.PaymentRepository;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Add a new payment (required)
    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Get all payments (required)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get payment by ID (required)
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    // Update payment (required)
    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = getPaymentById(id);
        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentMethod(paymentDetails.getPaymentMethod());
        return paymentRepository.save(payment);
    }

    // Delete payment (required)
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id); 
    }
    

    // Find payments by trip ID (required)
    public List<Payment> findPaymentsByTripId(Long tripId) {
        return paymentRepository.findPaymentsByTripId(tripId);
    }

    // Find payments with amount greater than threshold (required)
    public List<Payment> findByAmountThreshold(Double threshold) {
        return paymentRepository.findByAmountGreaterThan(threshold);
    }
    
    // Additional useful methods
    public List<Payment> findByPaymentMethod(String method) {
        return paymentRepository.findByPaymentMethod(method);
    }
    
    public List<Payment> findByPaymentStatus(Boolean status) {
        return paymentRepository.findByPaymentStatus(status);
    }
}
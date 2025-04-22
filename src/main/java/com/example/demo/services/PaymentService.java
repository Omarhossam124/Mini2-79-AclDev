package com.example.demo.services;
import com.example.demo.models.Payment;
import com.example.demo.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
     private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 8.4.2.1 Add Payment
    public Payment addPayment(Payment payment) {
        paymentRepository.addPayment(payment);
        return payment; // Return the added payment
    }

    // 8.4.2.2 Get All Payments
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments(); 
    }

    // 8.4.2.3 Get Payment By ID
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id); 
    }

    // 8.4.2.4 Update Payment
    public Payment updatePayment(Long id, Payment updatedPayment) {
        Payment existingPayment = paymentRepository.findById(id);
        if (existingPayment != null) {
            existingPayment.setAmount(updatedPayment.getAmount());
            existingPayment.setPaymentMethod(updatedPayment.getPaymentMethod());
            existingPayment.setPaymentStatus(updatedPayment.isPaymentStatus());
            return existingPayment; // Return updated payment
        }
        return null; // Or throw an exception
    }

    // 8.4.2.5 Delete Payment
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id); // Assuming a method exists in the repository
    }

    // 8.4.2.6 Find Payments By Trip ID
    public List<Payment> findPaymentsByTripId(Long tripId) {
        return paymentRepository.findPaymentsByTripId(tripId);
    }

    // 8.4.2.7 Find Payments With an Amount Greater Than a Threshold
    public List<Payment> findByAmountThreshold(Double threshold) {
        return paymentRepository.findPaymentsAboveThreshold(threshold);
    }
}

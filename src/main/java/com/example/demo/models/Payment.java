package com.example.demo.models;

public class Payment {
    private Long id; // Unique identifier for the payment
    private double amount;
    private String paymentMethod;
    private boolean paymentStatus;
    private Trip trip; // Assuming there's a Trip class

    // Constructor
    public Payment(Long id, double amount, String paymentMethod, boolean paymentStatus) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

       // Method to display payment details
       @Override
       public String toString() {
           return "Payment{" +
                   "id=" + id +
                   ", amount=" + amount +
                   ", paymentMethod='" + paymentMethod + '\'' +
                   ", paymentStatus=" + paymentStatus +
                   '}';
       }
}


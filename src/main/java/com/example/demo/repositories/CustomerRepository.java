package com.example.demo.repositories;

import com.example.demo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find customers by email domain (e.g., gmail.com)
    @Query("SELECT c FROM Customer c WHERE c.email LIKE %:domain")
    List<Customer> findByEmailDomain(@Param("domain") String domain);

    // Find customers by phone number prefix (e.g., starts with +20)
    @Query("SELECT c FROM Customer c WHERE c.phoneNumber LIKE :prefix%")
    List<Customer> findByPhonePrefix(@Param("prefix") String prefix);
}

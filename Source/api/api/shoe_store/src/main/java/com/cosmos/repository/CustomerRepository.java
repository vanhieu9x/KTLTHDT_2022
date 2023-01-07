package com.cosmos.repository;

import com.cosmos.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customers,Integer> {
    @Query(nativeQuery = true, value="select * from Customers item where item.email = ?1")
    Customers getCustomerByEmail(String n);
}

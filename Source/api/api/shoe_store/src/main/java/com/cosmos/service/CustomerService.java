package com.cosmos.service;

import com.cosmos.entity.Customers;
import com.cosmos.model.Register;
import com.cosmos.model.UpdateAccount;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    void createCustomer (Register rg);

    void updateCustomer (UpdateAccount account,Integer customersId);

    ResponseEntity<String> createCustomerR (Register rg);

}

package com.cosmos.controller;


import com.cosmos.entity.Customers;
import com.cosmos.model.UpdateAccount;
import com.cosmos.repository.CustomerRepository;
import com.cosmos.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer/")
public class CustomerController {
    private final AccountService accService;
    private final  CustomerRepository customerRepo;

    @PutMapping("/updateAccount")
    public ResponseEntity updateAccount(@Validated @RequestBody UpdateAccount account) {
        accService.updateAccount(account);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Optional<Customers> getCustomerById(@PathVariable("id") Integer id) {
        return customerRepo.findById(id);
    }

    @CrossOrigin
    @GetMapping("get-by-email")
    public Customers getCustomerByEmail(@Validated @RequestParam String email) {
        return customerRepo.getCustomerByEmail(email);
    }

    @CrossOrigin
    @GetMapping("/all")
    public  ResponseEntity<?> getListCustomer(){
        List<Customers> emp = customerRepo.findAll();
        return new ResponseEntity<List<Customers>>(emp, HttpStatus.OK);
    }


}

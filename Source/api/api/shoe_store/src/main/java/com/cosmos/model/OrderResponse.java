package com.cosmos.model;

import com.cosmos.entity.Customers;
import com.cosmos.entity.Employee;
import com.cosmos.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private int idorder;
    private String nameCustomer;
    private String address;
    private String phone;

    private Customers customer;
    private Employee employee;
    private float totalprice;
    private int status;
    private int ispaid;
    private Date createdate;
    private List<ProductResponse> product;


}


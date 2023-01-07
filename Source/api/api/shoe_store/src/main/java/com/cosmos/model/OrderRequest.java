package com.cosmos.model;

import com.cosmos.entity.Order_Detail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @NotEmpty(message = "can-not-empty")
    private String nameCustomer;

    @NotEmpty(message = "can-not-empty")
    private String address;

    @NotEmpty(message = "can-not-empty")
    private String phoneNumber;
    
    private int ispaid;
}

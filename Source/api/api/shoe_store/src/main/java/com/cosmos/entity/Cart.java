package com.cosmos.entity;

import com.cosmos.entity.keyID.Cart_id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cart {
    @EmbeddedId
    Cart_id id;

    @Column(name="number")
    private int number;

    @ManyToOne
    @MapsId("productid")
    @JoinColumn(name = "productid")
    Product product;

    @JsonIgnore
    @ManyToOne
    @MapsId("customerid")
    @JoinColumn(name = "customerid")
    Customers customer;
}

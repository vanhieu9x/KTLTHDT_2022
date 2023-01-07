package com.cosmos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Orders {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "customerid")
  private Customers customers;

  @ManyToOne
  @JoinColumn(name = "employeeid")
  private Employee employee;

  @Column(name = "createday")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date createday;

  @Column(name = "totalprice")
  private float totalprice;

  @Column(name = "namecustomer")
  private String namecustomer;

  @Column(name = "phonenumber")
  private String phoneNumber;

  @Column(name = "address")
  private String address;

  @Column(name = "status")
  private int status;

  @Column(name = "ispaid")
  private int ispaid;

  @OneToMany(mappedBy = "order")
  private List<Order_Detail> orderDetailList = new ArrayList<>();

}

package com.cosmos.entity;

import com.cosmos.entity.keyID.Order_Detail_Id;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Order_Detail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order_Detail {

  @EmbeddedId
  Order_Detail_Id order_detail_id;

  @Column(name = "number")
  private int number;

  @Column(name = "price")
  private float price;

  @ManyToOne
  @MapsId("productid")
  @JoinColumn(name = "productid")
  Product product;

  @ManyToOne
  @MapsId("orderid")
  @JoinColumn(name = "orderid")
  private Orders order;
}

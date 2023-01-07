package com.cosmos.repository;

import com.cosmos.entity.Order_Detail;
import com.cosmos.entity.keyID.Order_Detail_Id;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailRepository extends JpaRepository<Order_Detail, Order_Detail_Id> {

  List<Order_Detail> getAllByOrderId(Integer id);

  List<Order_Detail> findAllByOrderId(Integer id);

}

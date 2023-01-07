package com.cosmos.repository;

import com.cosmos.entity.Orders;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

  @Query(value = "SELECT MAX(id) FROM Order", nativeQuery = true)
  int maxID();

  @Query(value = "Select * from Orders od where od.createday>=:from and od.createday<=:to and od.status=4", nativeQuery = true)
  List<Orders> findAllByOrderCreatedayBetween(@Param("from") Date from,
      @Param("to") Date to);

  @Query(value = "Select * from Orders od where od.createday=:date", nativeQuery = true)
  List<Orders> findAllByOrderCreateday(@Param("date") Date date);

  @Query(value = "Select * from Orders od where od.customerid=:customerid", nativeQuery = true)
  List<Orders> findAllByIdCustomer(@Param("customerid") Integer customerid);

  @Query(value = "Select * from Orders od where od.status=4", nativeQuery = true)
  List<Orders> findAllByStatus();



}

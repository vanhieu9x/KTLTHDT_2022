package com.cosmos.service;

import com.cosmos.entity.*;
import com.cosmos.model.OrderRequest;
import com.cosmos.model.OrderResponse;

import java.util.List;

public interface OrderService {

    void CreateOrder (OrderRequest orderRequest, Customers customers);

    OrderResponse addToOrder();

  void changeOrderStatus(Employee employee,Integer status,Integer orderId);

    OrderResponse getOrderById(Integer orderId);

    List<OrderResponse> getAllOrder();

    List<OrderResponse> getAllOrderOfCustomer(Integer customerId);
}

package com.cosmos.controller;

import com.cosmos.entity.Cart;
import com.cosmos.model.OrderRequest;
import com.cosmos.model.OrderResponse;
import com.cosmos.security.service.UserPrinciple;
import com.cosmos.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order/")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> addToOrder(@Validated @RequestBody List<Cart> cart) {
        OrderResponse order = orderService.addToOrder();
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addToOrder(@Validated @RequestBody OrderRequest orderRequest) {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        orderService.CreateOrder(orderRequest, userPrinciple.getCustomer());
        return ResponseEntity.ok().build();
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getOrderbyId(@PathVariable  Integer id) {
        OrderResponse order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("all-by-customer-id")
    public ResponseEntity<?> getOrderbyIdCustomer(@RequestParam Integer customerId) {
        List<OrderResponse> order = orderService.getAllOrderOfCustomer(customerId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllOrder() {
        List<OrderResponse> order = orderService.getAllOrder();
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("change")
        public ResponseEntity<?> changeOrderStatus(@RequestParam Integer status,
                                                    @RequestParam Integer orderId){
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        orderService.changeOrderStatus(userPrinciple.getEmployee(), status, orderId);
        return ResponseEntity.ok().build();
    }
}

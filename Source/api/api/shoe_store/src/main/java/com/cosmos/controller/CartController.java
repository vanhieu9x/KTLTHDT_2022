package com.cosmos.controller;

import com.cosmos.entity.Cart;
import com.cosmos.model.CartRequest;
import com.cosmos.repository.CartRepository;
import com.cosmos.security.service.UserPrinciple;
import com.cosmos.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart/")
public class CartController {

    private final CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@Validated  @RequestBody CartRequest cartRequest){
        cartService.addToCart(cartRequest);

        return  ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete")
    public  ResponseEntity<?> deleteCart(@Validated @RequestParam Integer productid){
        cartService.deleteToCart(productid);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/all")
    public  ResponseEntity<List<Cart>> getCart(){
List<Cart> cart = cartService.getCart();
        return new ResponseEntity<List<Cart>>(cart,HttpStatus.OK);
    }
}

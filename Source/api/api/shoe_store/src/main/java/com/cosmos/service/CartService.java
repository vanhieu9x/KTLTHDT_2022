package com.cosmos.service;

import com.cosmos.entity.Cart;
import com.cosmos.model.CartRequest;

import java.util.List;

public interface CartService {
    void addToCart(CartRequest cartRequest);

    void deleteToCart(int productid);

   List<Cart> getCart();
}

package com.cosmos.repository;

import com.cosmos.entity.Cart;
import com.cosmos.entity.keyID.Cart_id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Cart_id> {

    List<Cart> getAllByCustomer_Id(Integer id);
}

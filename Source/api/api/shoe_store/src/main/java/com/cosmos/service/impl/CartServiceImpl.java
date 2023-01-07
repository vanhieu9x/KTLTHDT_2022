package com.cosmos.service.impl;

import com.cosmos.entity.Cart;
import com.cosmos.entity.Customers;
import com.cosmos.entity.Product;
import com.cosmos.entity.keyID.Cart_id;
import com.cosmos.model.CartRequest;
import com.cosmos.repository.CartRepository;
import com.cosmos.security.service.UserPrinciple;
import com.cosmos.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;

    @Override
    @Transactional
    public void addToCart(CartRequest cartRequest) {
     try{
         Product product = new Product();
        product.setId(cartRequest.getProductid());

        Customers custome = new Customers();
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        custome.setId(userPrinciple.getCustomer().getId());

        System.out.println(userPrinciple.getCustomer().getId());
        System.out.println(cartRequest.getProductid());
        System.out.println(cartRequest.getNumber());

        Cart cart = new Cart();
        Cart_id id = new Cart_id(userPrinciple.getCustomer().getId(),cartRequest.getProductid());
        cart.setId(id);


        cart.setCustomer(custome);
        cart.setProduct(product);
        cart.setNumber(cartRequest.getNumber());

        cartRepo.save(cart);
     }catch (Exception ex){
         System.out.println(ex);
     }
    }

    @Override
    @Transactional
    public void deleteToCart(int productid) {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Cart_id id = new Cart_id(userPrinciple.getCustomer().getId(),productid);
        cartRepo.deleteById(id);
    }

    @Transactional
    @Override
    public List<Cart> getCart() {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<Cart> cartList =    cartRepo.getAllByCustomer_Id(userPrinciple.getCustomer().getId());
        return cartList;
    }
}

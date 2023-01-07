package com.cosmos.repository;

import com.cosmos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(nativeQuery = true, value="select * from Products p where p.name = ?1")
    Product getProductByName(String n);

    @Query(nativeQuery = true, value="select * from Products p where p.categoryid = ?1")
    List<Product> getProductByIdCategory(Integer categoryid);

    @Query(nativeQuery = true, value="select * from Products p where p.manufactureid = ?1")
    List<Product> getProductByIdManufacture(Integer manufactureid);

    @Override
    Product getById(Integer integer);

//@Query(nativeQuery = true, value = "select distinct u from User u join u.roles r where r.id =:role_id")
    //List
}

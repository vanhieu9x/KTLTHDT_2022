package com.cosmos.repository;

import com.cosmos.entity.Fragrance;
import com.cosmos.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(nativeQuery = true, value="select * from Images i where i.productid = ?1")
    List<Image> getImageByIdProduct(Integer productid);
}

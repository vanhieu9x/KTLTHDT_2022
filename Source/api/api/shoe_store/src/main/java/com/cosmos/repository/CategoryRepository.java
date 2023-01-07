package com.cosmos.repository;

import com.cosmos.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(nativeQuery = true, value="select * from Categories cate where cate.name = ?1")
    Category getCategoryByName(String n);
}

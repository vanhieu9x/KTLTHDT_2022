package com.cosmos.repository;

import com.cosmos.entity.Fragrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FragranceRepository extends JpaRepository<Fragrance, Integer> {
    @Query(nativeQuery = true, value="select * from Fragrances f where f.name = ?1")
    Fragrance getFragranceByName(String n);
}

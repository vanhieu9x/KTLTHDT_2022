package com.cosmos.repository;

import com.cosmos.entity.Manufacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManufactureRepository extends JpaRepository<Manufacture, Integer> {
    @Query(nativeQuery = true, value="select * from Manufactures m where m.name = ?1")
    Manufacture getManufactureByName(String n);
}


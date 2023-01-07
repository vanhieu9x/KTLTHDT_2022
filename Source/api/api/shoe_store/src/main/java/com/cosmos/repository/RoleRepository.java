package com.cosmos.repository;

import com.cosmos.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(nativeQuery = true, value="select * from Roles cate where cate.name = ?1")
    Role getRoleByName(String n);
}
package com.cosmos.repository;

import com.cosmos.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(nativeQuery = true, value= "Select * from Employees where name = ?1")
    Employee getEmployeeByName(String name);

    @Query(nativeQuery = true, value= "Select * from Employees where email = ?1")
    Employee getEmployeeByEmail(String email);
}

package com.cosmos.service.impl;

import com.cosmos.entity.Account;
import com.cosmos.entity.Customers;
import com.cosmos.entity.Employee;
import com.cosmos.entity.Role;
import com.cosmos.model.Register;
import com.cosmos.model.UpdateAccount;
import com.cosmos.repository.AccountRepository;
import com.cosmos.repository.EmployeeRepository;
import com.cosmos.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor

public class EmployeeImpl implements EmployeeService {

   private final PasswordEncoder passwordEncoder;
    private  final AccountRepository repo;
    private final EmployeeRepository employeeRepository;
    @Override
    @Transactional
    public void addToEmployee(Register register) {
        Account acc = repo.findTaiKhoanByEmail(register.getEmail());
        if (acc != null) {
            return;
        } else {
            try {
                Account newAcc = new Account();
                Employee newEmployee = new Employee();
              String pass =  passwordEncoder.encode(register.getPassword());
                newAcc.setEmail(register.getEmail());

                newAcc.setPassword(pass);
                newAcc.setRole(new Role(2));

                newEmployee.setEmail(register.getEmail());
                newEmployee.setGender(register.isGender());
                newEmployee.setName(register.getName());
                newEmployee.setAddress(register.getAddress());
                newEmployee.setPhone(register.getPhone());

                repo.save(newAcc);
                employeeRepository.save(newEmployee);
                return ;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        return;
    }

    @Override
    @Transactional
    public void updateCustomer(UpdateAccount account, Integer employeeId) {
        Employee newEmployee = employeeRepository.getById(employeeId) ;

        newEmployee.setAddress(account.getAddress());
        newEmployee.setName(account.getName());
        newEmployee.setPhone(account.getPhonenumber());
        newEmployee.setGender(account.isGender());

        employeeRepository.save(newEmployee);


    }
}

package com.cosmos.service.impl;

import com.cosmos.entity.Account;
import com.cosmos.entity.Customers;
import com.cosmos.entity.Employee;
import com.cosmos.entity.Role;
import com.cosmos.model.Register;
import com.cosmos.model.UpdateAccount;
import com.cosmos.repository.AccountRepository;
import com.cosmos.repository.CustomerRepository;
import com.cosmos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service
public class CustomerImpl implements CustomerService {

    private final AccountRepository accRepo;
    private final CustomerRepository customerRepo;

    private final  PasswordEncoder passwordEncode;

    @Override
    @Transactional
    public void createCustomer(Register rg) {
        Account acc = accRepo.findTaiKhoanByEmail(rg.getEmail());
        if (acc != null) {
            System.out.printf("Email tồn tại");
        } else {
            try {
                Account newAcc = new Account();
                Customers newCustomer = new Customers();

                newAcc.setEmail(rg.getEmail());

                newAcc.setPassword( passwordEncode.encode(rg.getPassword()));
                newAcc.setRole(new Role(1));

                newCustomer.setEmail(rg.getEmail());
                newCustomer.setGender(rg.isGender());
                newCustomer.setName(rg.getName());
                newCustomer.setAddress(rg.getAddress());
                newCustomer.setPhone(rg.getPhone());

                accRepo.save(newAcc);
                customerRepo.save(newCustomer);
                System.out.printf("Tao thanh cong");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }
    @Override
    @Transactional
    public ResponseEntity<String> createCustomerR(Register rg) {
        Account acc = accRepo.findTaiKhoanByEmail(rg.getEmail());
        if (acc != null) {
            System.out.printf("Email tồn tại");
            return new ResponseEntity<String>("Email đã được đăng ký tài khoản, xin dùng email khác!" , HttpStatus.BAD_REQUEST);
        } else {
            try {
                Account newAcc = new Account();
                Customers newCustomer = new Customers();

                newAcc.setEmail(rg.getEmail());

                newAcc.setPassword( passwordEncode.encode(rg.getPassword()));
                newAcc.setRole(new Role(1));

                newCustomer.setEmail(rg.getEmail());
                newCustomer.setGender(rg.isGender());
                newCustomer.setName(rg.getName());
                newCustomer.setAddress(rg.getAddress());
                newCustomer.setPhone(rg.getPhone());

                accRepo.save(newAcc);
                customerRepo.save(newCustomer);
                System.out.printf("Tao thanh cong");
                return new ResponseEntity<String>("successed !!!",HttpStatus.OK);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        return new ResponseEntity<String>("failed !!!", HttpStatus.BAD_REQUEST);
    }
    @Override
    @Transactional
    public void updateCustomer(UpdateAccount account, Integer customersId) {
            Customers newCustomer = customerRepo.getById(customersId) ;

            newCustomer.setAddress(account.getAddress());
            newCustomer.setName(account.getName());
            newCustomer.setPhone(account.getPhonenumber());
            newCustomer.setGender(account.isGender());

            customerRepo.save(newCustomer);


    }



}

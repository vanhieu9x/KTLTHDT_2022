//package com.cosmos.controller;
//
//import com.cosmos.entity.Account;
//import com.cosmos.entity.Role;
//import com.cosmos.model.Login;
//import com.cosmos.repository.AccountRepository;
//import com.cosmos.repository.EmployeeRepository;
//import com.cosmos.response.JwtResponse;
//import com.cosmos.service.AccountService;
//import com.cosmos.service.AuthService;
//import com.cosmos.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class AccountController {
//    @Autowired
//    AccountRepository repo;
//
//    private final AccountService accService;
//    private final AuthService authService;
//    private final CustomerService customerService;
//
//    @Autowired
//    EmployeeRepository employeeRepo;
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> login(@Validated @RequestBody Login login) {
//        return ResponseEntity.ok(authService.authenticateUser(login));
//    }
//
//    @CrossOrigin
//    @PutMapping("/account")
//    public ResponseEntity<String> updateAccount(@Validated @RequestBody Account update){
//        try {
//            List<Account> list = repo.findAll();
//
//            for(Account item:list) {
//                if(item.getEmail().trim().equals(update.getEmail().trim())) {
//                    repo.save(update);
//                    return new ResponseEntity<String>("Successed" , HttpStatus.OK);
//                }
//            }
//            return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
//    }
//}
//



package com.cosmos.controller;

import com.cosmos.entity.*;
import com.cosmos.model.Login;
import com.cosmos.model.Register;
import com.cosmos.model.UpdateAccount;
import com.cosmos.repository.AccountRepository;
import com.cosmos.repository.ImageRepository;
import com.cosmos.repository.ProductRepository;
import com.cosmos.response.JwtResponse;
import com.cosmos.service.AccountService;
import com.cosmos.service.AuthService;
import com.cosmos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AccountController {

    @Autowired
    AccountRepository repo;

    private final AccountService accService;
    private final AuthService authService;
    private final CustomerService customerService;

    @CrossOrigin
    @GetMapping("email={email}")
    public Account getCategoryByName(@PathVariable("email") String email) {
        return repo.findTaiKhoanByEmail(email);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Validated @RequestBody Login login) {
        return ResponseEntity.ok(authService.authenticateUser(login));
    }

    @PostMapping("/register")
//    public ResponseEntity<?> createCustomer(@Validated @RequestBody Register register){
//        customerService.createCustomer(register);
//        return ResponseEntity.ok().build();
//    }
    public ResponseEntity<?> createCustomer(@Validated @RequestBody Register register){
        return  customerService.createCustomerR(register);
        //return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PutMapping("/account") //edit employee
    public ResponseEntity<String> updateAccount(@Validated @RequestBody Account update){
        try {
            List<Account> list = repo.findAll();

            for(Account item:list) {
                if(item.getEmail().trim().equals(update.getEmail().trim())) {
                    repo.save(update);
                    return new ResponseEntity<String>("Successed" , HttpStatus.OK);
                }
            }
            return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
    }

//    @CrossOrigin
//    @GetMapping("/account/info")
//    public <T> getUserByEmail(@Validated @RequestBody Account acc){
//        if(acc.getRole().getRole() == 1)
//        return new ResponseEntity<List<Employee>>(emp,HttpStatus.OK);
//    }

    //------------------//
//    @Autowired
//    private ProductRepository ProductRepo;
//
//    @Autowired
//    private ImageRepository image_repo;
//
//    @CrossOrigin
//    @GetMapping("/product")
//    public List<Product> getListProduct(){
//        try {
//            List<Product> list = ProductRepo.findAll();
//            for(Product item:list) {
//                List<Fragrance> lf = item.getFragrances();
//                List<Image> li = image_repo.getImageByIdProduct(item.getId());
//                item.setImages(li);
//                item.setFragrances(lf);
//            }
//            //return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
//            return list;
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        //return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
//        return null;
//    }
}


package com.cosmos.controller;

import com.cosmos.entity.Account;
import com.cosmos.entity.Employee;
import com.cosmos.entity.Product;
import com.cosmos.entity.Role;
import com.cosmos.model.Register;
import com.cosmos.repository.AccountRepository;
import com.cosmos.repository.EmployeeRepository;
import com.cosmos.service.CartService;
import com.cosmos.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    AccountRepository repo;

    @Autowired
    EmployeeRepository employeeRepo;

    @Autowired
    private final PasswordEncoder  passwordEncode;

    private final EmployeeService employeeService;

    public static String uploadDirectory = System.getProperty("user.dir")+"src/main/resources/images/product";
    public static String staticDirectory = System.getProperty("user.dir")+"src/main/resources/static";

    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @PostMapping("/employee/register")
    public ResponseEntity<?> register(@Validated @RequestBody Register rg) {
             employeeService.addToEmployee(rg);
             return  ResponseEntity.ok().build();

    }
    @GetMapping("/employee/all")
    public  ResponseEntity<?> getListEmployee(){
        List<Employee> emp = employeeRepo.findAll();
        return new ResponseEntity<List<Employee>>(emp,HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/employee/{id}")
    public Optional<Employee> getProductById(@PathVariable("id") Integer id) {
        return employeeRepo.findById(id);
    }


    @GetMapping("/employee/name={name}")
    public  ResponseEntity<?> getEmployeeByName(@PathVariable String name){
         Employee emp = employeeRepo.getEmployeeByName(name);
         return new ResponseEntity<Employee>(emp,HttpStatus.OK);
    }

    @GetMapping("/employee/email={email}")
    public  ResponseEntity<?> getEmployeeByEmail(@PathVariable String email){
        Employee emp = employeeRepo.getEmployeeByEmail(email);
        return new ResponseEntity<Employee>(emp,HttpStatus.OK);
    }

    @PutMapping("/employee/update")
    public  ResponseEntity<?> updateEmployeeByName(@Validated @RequestBody Employee employee){
        Employee emp = employeeRepo.getEmployeeByEmail(employee.getEmail());
        if(emp == null ){
            return new ResponseEntity<String>("Thay doi thong tin không thành cônng", HttpStatus.BAD_REQUEST);
        }else
            try{
//                String url
//                Path staticPath = Paths.get("static");
//                Path imagesPath = Paths.get("images");
//                Path projectPath = Paths.get("product");
//                Path m = CURRENT_FOLDER.resolve(staticPath).resolve(imagesPath).resolve(projectPath);
//                if (!Files.exists(m)) {
//                    Files.createDirectories(m);
//                }
//                String imgname = stringi + item.getOriginalFilename();
//                Path imgPath = m.resolve(imgname);
//                try (OutputStream os = Files.newOutputStream(imgPath)) {
//                    os.write(item.getBytes());
//                }
//                //save i
//
//                emp.setImage(imagesPath.resolve(projectPath).resolve(imgname).toString());
                employeeRepo.save(employee);
                return new ResponseEntity<Employee>( employee, HttpStatus.OK);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        return new ResponseEntity<String>("Thay doi thong tin không thành cônng", HttpStatus.BAD_REQUEST);
    }

}

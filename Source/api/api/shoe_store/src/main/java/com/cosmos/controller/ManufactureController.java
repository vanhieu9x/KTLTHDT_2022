package com.cosmos.controller;


import com.cosmos.entity.Category;
import com.cosmos.entity.Manufacture;
import com.cosmos.entity.Product;
import com.cosmos.repository.ManufactureRepository;
import com.cosmos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manufacture")
public class ManufactureController {
    @Autowired
    private ManufactureRepository repo;
    @Autowired
    private ProductRepository product_repo;

    @CrossOrigin
    @GetMapping
    public List<Manufacture> getListManufacture(){
        return repo.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Optional<Manufacture> getManufactureById(@PathVariable("id") Integer id) {
        return repo.findById(id);
    }

    @CrossOrigin
    @GetMapping("/name={name}")
    public Manufacture getManufactureByName(@PathVariable("name") String n) {
        return repo.getManufactureByName(n);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> creatNewManufacture(@Validated @RequestBody Manufacture newitem){
        try {
            List<Manufacture> list = repo.findAll();
            for(Manufacture item:list) {
                if(item.getName().trim().toLowerCase().equals(newitem.getName().trim().toLowerCase()) == true) {
                    return new ResponseEntity<String>("This name already exists!" , HttpStatus.BAD_REQUEST);
                }
            }
            repo.save(newitem);
            return new ResponseEntity<String>("successed !!!", HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!", HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@Validated @RequestBody Manufacture update){
        try {
            List<Manufacture> list = repo.findAll();
            for(Manufacture item:list) {
                if(item.getName().trim().toLowerCase().equals(update.getName().trim().toLowerCase()) == true && item.getId() != update.getId()) {
                    return new ResponseEntity<String>("This name already exists!" , HttpStatus.BAD_REQUEST);
                }
            }
            for(Manufacture item:list) {
                if(item.getId() == update.getId()) {
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

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer id){
        try {
            List<Product> lp = new ArrayList<>();
            lp = product_repo.getProductByIdManufacture(id);
            if (lp.size() != 0){
                return new ResponseEntity<String>("This manufacture have product!" , HttpStatus.BAD_REQUEST);
            }
            repo.deleteById(id);
            return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
    }
}

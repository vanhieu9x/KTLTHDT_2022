package com.cosmos.controller;

import com.cosmos.entity.Category;
import com.cosmos.entity.Product;
import com.cosmos.repository.CategoryRepository;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryRepository repo;
    @Autowired
    private ProductRepository product_repo;

    @CrossOrigin
    @GetMapping
    public List<Category> getListCategory(){
        return repo.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Optional<Category> getCategoryById(@PathVariable("id") Integer id) {
        return repo.findById(id);
    }

    @CrossOrigin
    @GetMapping("/name={name}")
    public Category getCategoryByName(@PathVariable("name") String n) {
        return repo.getCategoryByName(n);

    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> creatNewCategory(@Validated @RequestBody Category categorynew){
        try {
            List<Category> listCategory = repo.findAll();
            for(Category cateitem:listCategory) {
                if(cateitem.getName().trim().toLowerCase().equals(categorynew.getName().trim().toLowerCase()) == true) {
                    //System.out.println("kkkkkkkkkkkk: "+cateitem.getName().toLowerCase() + categorynew.getName().toLowerCase());
                    return new ResponseEntity<String>("This name already exists!" , HttpStatus.BAD_REQUEST);
                }
            }
            repo.save(categorynew);
            return new ResponseEntity<String>("successed !!!",HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!", HttpStatus.BAD_REQUEST);
    }

//    @CrossOrigin
//    @PostMapping
//    public ResponseEntity<String> creatNewCategory(@Validated @RequestBody String name,@RequestBody String description){
//        Category nc = new Category();
//        nc.setName(name);
//        nc.setDescription(description);
//        try {
//            repo.save(nc);
//            return new ResponseEntity<String>("successed !!!",HttpStatus.OK);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return new ResponseEntity<String>("failed !!!", HttpStatus.BAD_REQUEST);
//    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@Validated @RequestBody Category cateupdate){
        try {
            List<Category> listCategory = repo.findAll();
            for(Category cateitem:listCategory) {
                if(cateitem.getName().trim().toLowerCase().equals(cateupdate.getName().trim().toLowerCase()) == true && cateitem.getId() != cateupdate.getId()) {
                    //System.out.println("kkkkkkkkkkkk: "+cateitem.getName().toLowerCase() + cateupdate.getName().toLowerCase());
                    return new ResponseEntity<String>("This name already exists!" , HttpStatus.BAD_REQUEST);
                }
            }
            for(Category cateitem:listCategory) {
                if(cateitem.getId() == cateupdate.getId()) {
                    repo.save(cateupdate);
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
            lp = product_repo.getProductByIdCategory(id);
            if (lp.size() != 0){
                return new ResponseEntity<String>("This category have product!" , HttpStatus.BAD_REQUEST);
            }
            repo.deleteById(id);
            return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
    }

//    @DeleteMapping
//    public ResponseEntity<String> deleteManyCategory(int[] ids){
//        try {
//            //repo.deleteAllById(Arrays.asList(ids));
//            for(int item: ids){
//                repo.deleteById(item);
//            }
//            return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
//    }




}


package com.cosmos.controller;

import com.cosmos.entity.Fragrance;
import com.cosmos.repository.FragranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fragrance")
public class FragranceController {
    @Autowired
    private FragranceRepository repo;

    @CrossOrigin
    @GetMapping
    public List<Fragrance> getListCategory(){
        return repo.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Optional<Fragrance> getFragranceById(@PathVariable("id") Integer id) {
        return repo.findById(id);
    }

    @CrossOrigin
    @GetMapping("/name={name}")
    public Fragrance getFragranceByName(@PathVariable("name") String n) {
        return repo.getFragranceByName(n);

    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> creatNewFragrance(@Validated @RequestBody Fragrance newitem){
        try {
            List<Fragrance> list = repo.findAll();
            for(Fragrance item:list) {
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
    public ResponseEntity<String> updateFragrance(@Validated @RequestBody Fragrance update){
        try {
            List<Fragrance> list = repo.findAll();
            for(Fragrance item:list) {
                if(item.getName().trim().toLowerCase().equals(update.getName().trim().toLowerCase()) == true && item.getId() != update.getId()) {
                    return new ResponseEntity<String>("This name already exists!" , HttpStatus.BAD_REQUEST);
                }
            }
            for(Fragrance item:list) {
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
    public ResponseEntity<String> deleteFragrance(@PathVariable("id") Integer id){
        try {
            //List<Product> lp = new ArrayList<>();
            //lp = lp.get;
            Fragrance m = repo.getById(id);
            if (m.getProductList().size() != 0){
                return new ResponseEntity<String>("This fragrance have product!" , HttpStatus.BAD_REQUEST);
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

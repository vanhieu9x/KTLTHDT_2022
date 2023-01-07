package com.cosmos.controller;


import com.cosmos.entity.Role;
import com.cosmos.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleRepository repo;

    @CrossOrigin
    @GetMapping
    public List<Role> getListCategory(){
        return repo.findAll();
    }


}

package com.example.dependent;


import com.example.dependent.Pojo.Dependent;
import com.example.dependent.Repository.DependentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    DependentRepo dependentRepo;

    @GetMapping("/getDependent/{id}")
    public Dependent getDependent(@PathVariable String id){ return dependentRepo.getOne(Integer.parseInt(id)); }

    @GetMapping("/getDependents")
    public List<Dependent> getDependents (){
        return dependentRepo.findAll();
    }



}

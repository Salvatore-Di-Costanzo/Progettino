package com.example.turni.Controller;

import com.example.turni.Pojo.Turni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map.Entry;

@RestController
public class Controller {

    private EntityManager entityManager;

    public Controller(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    FeignDependent feignDependent;

    @GetMapping("/getTurni")
    public List<Turni> getTurni () {


    }


}

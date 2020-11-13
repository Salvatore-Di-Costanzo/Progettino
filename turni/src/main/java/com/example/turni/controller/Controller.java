package com.example.turni.controller;

import com.example.turni.client.FeignDependent;
import com.example.turni.pojo.Turno;
import com.example.turni.repository.TurnoRepo;
import com.example.turni.service.RealizzaTurni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;


@RestController
@Configuration
@ComponentScan(basePackages = {"com.example.turni"})
public class Controller {

    private EntityManager entityManager;

    public Controller(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    RealizzaTurni realizzaTurni;

    @Autowired
    TurnoRepo turnoRepo;

    @Autowired
    FeignDependent feignDependent;

    @GetMapping("/setTurni/{numGiorni}")
    public String setTurni(@PathVariable String numGiorni){

        return realizzaTurni.calcolaTurni(Integer.parseInt(numGiorni));

    }

    @GetMapping("/getTurni")
    public List<Turno> getTurni(){

        return turnoRepo.findAll();

    }

    @GetMapping("/getDependentTurni/{data}")
    public String getDependentTurni (@PathVariable String data){

        return realizzaTurni.getDependentTurni(data);
    }


}

package com.example.turni.Controller;

import com.example.turni.Pojo.Turno;
import com.example.turni.Repository.TurnoRepo;
import com.netflix.discovery.converters.Auto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
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
    public String getDependentTurni (@PathVariable Date data){

        StringBuilder result = new StringBuilder();

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery =
                currentSession.createQuery("select id_dependent from Turno where date=:dateSet",Integer.class);
        theQuery.setParameter("dateSet",data);


        List<Integer> id_dipendenti = theQuery.getResultList();

        for(Integer id : id_dipendenti){
            result.append(feignDependent.getDateDependent(id));
            result.append("\n");
        }

        return result.toString();
    }


}

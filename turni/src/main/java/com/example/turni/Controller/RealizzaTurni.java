package com.example.turni.Controller;

import com.example.turni.Pojo.Turno;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@EnableFeignClients
@Slf4j
public class RealizzaTurni {

    @Autowired
    FeignDependent feignDependent;

    private EntityManager entityManager;

    Date currentDate = new Date();

    Date fine;

    int countDays = 0;




    public RealizzaTurni(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String calcolaTurni(int numGiorni){

        /// Importo gli Id dei dipendenti
        List<Integer> id_dependent = feignDependent.getIds();

        /// Algoritmo generazione turni
        for(int i = 1; i < numGiorni * 4 + 1; i++) {

            // Calcolo la data
            Date setDate = new Date(currentDate.getTime() + (1000*60*60*24) * countDays);

            log.info("\nData Elaborata: " + setDate.toString());


            // Verifico se devo avanzare con la data, poichè il numero max di dipendenti è 4 per ogni giorno
            // ogni qualvolta elaboro 4 dipendenti passo al giorno dopo.
            if (i%4 == 0 ) { countDays++; }



            // Mappo il turno da inserire
            Turno turno = new Turno();
            turno.setDate(setDate);
            int randomIdex = ThreadLocalRandom.current().nextInt(0, id_dependent.size());
            turno.setId_dependent(id_dependent.get(randomIdex));

            log.info("\nTurno.date: " + turno.getDate().toString());

            log.info("\nTurno.idDependent: " + turno.getId_dependent().toString());


            log.info("\nIndice elaborato: " + randomIdex);


            /// Rimuovo dalla lista l'ID dell'utente in modo che non venga ripescato
            id_dependent.remove(randomIdex);

            for(Integer element : id_dependent)
                log.info("\n->" + element);

            /// Verifico che la lista non debba essere ricaricata
            if (id_dependent.size() == 0){
                id_dependent = feignDependent.getIds();
                id_dependent.remove(randomIdex);
            }

            /// Inserisco il turno del DB
            Session currentSession = entityManager.unwrap(Session.class);

            currentSession.beginTransaction();
            currentSession.save(turno);
            currentSession.getTransaction().commit();

        }
        return "Calcolo turni effettuato dal: " + currentDate.toString() + " al :" + new Date(currentDate.getTime() + (1000*60*60*24) * numGiorni).toString();

    }

}

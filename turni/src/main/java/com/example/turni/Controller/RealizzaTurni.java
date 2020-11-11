package com.example.turni.Controller;

import com.example.turni.Pojo.Turno;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private static final String FORMATDATE = "dd-MM-yyyy";

    Date currentDate = new Date();

    Date fine;

    int countDays = 0;

    public RealizzaTurni(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String calcolaTurni(int numGiorni){

        /// Importo gli Id dei dipendenti
        List<Integer> id_dependent = feignDependent.getIds();

        List<Integer> id_giornata = new ArrayList<>();
        id_giornata.clear();

        /// Algoritmo generazione turni
        for(int i = 1; i < numGiorni * 4 +1 ; i++) {

            // Calcolo la data
            Date setDate = new Date(currentDate.getTime() + (1000*60*60*24) * countDays);


            // Verifico se devo avanzare con la data, poichè il numero max di dipendenti è 4 per ogni giorno
            // ogni qualvolta elaboro 4 dipendenti passo al giorno dopo.
            if (i%4 == 0 ) {
                countDays++;
                id_giornata.clear();
            }

            // Mappo il turno da inserire
            Turno turno = new Turno();
            turno.setDate(new SimpleDateFormat(FORMATDATE).format(setDate));
            int randomIdex = ThreadLocalRandom.current().nextInt(0, id_dependent.size());
            while (id_giornata.contains(id_dependent.get(randomIdex)))
                randomIdex = ThreadLocalRandom.current().nextInt(0, id_dependent.size());

            turno.setId_dependent(id_dependent.get(randomIdex));

            id_giornata.add(id_dependent.get(randomIdex));


            /// Rimuovo dalla lista l'ID dell'utente in modo che non venga ripescato
            id_dependent.remove(randomIdex);



            /// Verifico che la lista non debba essere ricaricata
            if (id_dependent.isEmpty()){
                id_dependent.addAll(feignDependent.getIds());
                id_dependent.removeAll(id_giornata);
            }

            /// Inserisco il turno del DB

            Session currentSession = entityManager.unwrap(Session.class);

            currentSession.beginTransaction();
            currentSession.save(turno);
            currentSession.getTransaction().commit();

        }
        return "Calcolo turni effettuato dal: " + new SimpleDateFormat(FORMATDATE).format(currentDate) + " al: " + new SimpleDateFormat(FORMATDATE).format(new Date(currentDate.getTime() + (1000*60*60*24) * (numGiorni -1)));

    }

}

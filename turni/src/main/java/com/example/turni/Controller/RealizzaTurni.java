package com.example.turni.Controller;

import com.example.turni.Pojo.Turno;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    LocalDate currentDate = LocalDate.now();

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
        for(int i = 1; i <= numGiorni * 4; i++) {

            // Calcolo la data
            LocalDate setDate = LocalDate.now().plusDays(countDays);


            // Verifico se devo avanzare con la data, poichè il numero max di dipendenti è 4 per ogni giorno
            // ogni qualvolta elaboro 4 dipendenti passo al giorno dopo.
            if (i%4 == 0 ) {
                countDays++;
                id_giornata.clear();
            }

            // Mappo il turno da inserire
            Turno turno = new Turno();
            StringBuilder data = new StringBuilder();
            data.append(setDate.getDayOfMonth());
            data.append("-");
            data.append(setDate.getMonthValue());
            data.append("-");
            data.append(setDate.getYear());
            log.info("Data caricata: " + data.toString());
            turno.setDate(data.toString());
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

        LocalDate setDate = LocalDate.now();

        LocalDateTime localDate = LocalDateTime.now().plusDays(numGiorni);

        StringBuilder dataInizio = new StringBuilder();
        dataInizio.append(setDate.getDayOfMonth());
        dataInizio.append("-");
        dataInizio.append(setDate.getMonthValue());
        dataInizio.append("-");
        dataInizio.append(setDate.getYear());

        StringBuilder dataFine = new StringBuilder();
        dataFine.append(localDate.getDayOfMonth());
        dataFine.append("-");
        dataFine.append(localDate.getMonthValue());
        dataFine.append("-");
        dataFine.append(localDate.getYear());



        return "Calcolo turni effettuato dal: " + dataInizio.toString() + " al: " + dataFine.toString();

    }

}

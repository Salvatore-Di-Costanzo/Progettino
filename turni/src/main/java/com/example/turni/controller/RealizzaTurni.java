package com.example.turni.controller;

import com.example.turni.pojo.Turno;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
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

    LocalDate currentDate = LocalDate.now();

    Date fine;

    int countDays = 0;

    public RealizzaTurni(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String calcolaTurni(int numGiorni){

        /// Importo gli Id dei dipendenti
        List<Integer> idDependent = feignDependent.getIds();

        List<Integer> idGiornata = new ArrayList<>();
        idGiornata.clear();

        StringBuilder dataFine = new StringBuilder();

        /// Algoritmo generazione turni
        for(int i = 1; i <= numGiorni * 4; i++) {

            // Calcolo la data

            // Se il giorno è Sabato o Domenica saltalo
            while ((LocalDate.now().plusDays(countDays).getDayOfWeek() == DayOfWeek.SATURDAY ||
                    LocalDate.now().plusDays(countDays).getDayOfWeek() == DayOfWeek.SUNDAY))
                countDays++;
            LocalDate setDate = LocalDate.now().plusDays(countDays);


            // Verifico se devo avanzare con la data, poichè il numero max di dipendenti è 4 per ogni giorno
            // ogni qualvolta elaboro 4 dipendenti passo al giorno dopo.
            if (i%4 == 0 ) {
                countDays++;
                idGiornata.clear();
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
            int randomIdex = ThreadLocalRandom.current().nextInt(0, idDependent.size());
            while (idGiornata.contains(idDependent.get(randomIdex)))
                randomIdex = ThreadLocalRandom.current().nextInt(0, idDependent.size());

            turno.setIdDependent(idDependent.get(randomIdex));

            idGiornata.add(idDependent.get(randomIdex));


            /// Rimuovo dalla lista l'ID dell'utente in modo che non venga ripescato
            idDependent.remove(randomIdex);



            /// Verifico che la lista non debba essere ricaricata
            if (idDependent.isEmpty()){
                idDependent.addAll(feignDependent.getIds());
                idDependent.removeAll(idGiornata);
            }

            /// Inserisco il turno del DB

            Session currentSession = entityManager.unwrap(Session.class);

            currentSession.beginTransaction();
            currentSession.save(turno);
            currentSession.getTransaction().commit();

            if (i == numGiorni * 4){
                dataFine.append(setDate.getDayOfMonth());
                dataFine.append("-");
                dataFine.append(setDate.getMonthValue());
                dataFine.append("-");
                dataFine.append(setDate.getYear());
            }

        }

        LocalDate setDate = LocalDate.now();

        StringBuilder dataInizio = new StringBuilder();
        dataInizio.append(setDate.getDayOfMonth());
        dataInizio.append("-");
        dataInizio.append(setDate.getMonthValue());
        dataInizio.append("-");
        dataInizio.append(setDate.getYear());





        return "Calcolo turni effettuato dal: " + dataInizio.toString() + " al: " + dataFine.toString();

    }

}

package com.example.turni.service;

import com.example.turni.client.FeignDependent;
import com.example.turni.pojo.Turno;
import com.example.turni.repository.TurnoRepo;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
@EnableFeignClients
public class RealizzaTurni {

    @Autowired
    FeignDependent feignDependent;

    @Autowired
    TurnoRepo repository;

    private EntityManager entityManager;

    public RealizzaTurni(EntityManager entityManager) {
        this.entityManager = entityManager;
    }





    public String calcolaTurni(int numGiorni , int nDipendenti) {

        int indexg = 0;
        int countDays = 0;
        boolean check = true;

        int unique_id = ThreadLocalRandom.current().nextInt(0, 1000);


        LocalDate baseDate = LocalDate.parse(getMaxDate());
        LocalDate setDate;

        /// Importo gli Id dei dipendenti
        List<String> idDependent = feignDependent.GetAllIndex();

        List<String> idGiornata = new ArrayList<>();

        StringBuilder dataFine = new StringBuilder();

        /// Algoritmo generazione turni
        for (int i = 1; i <= numGiorni * nDipendenti; i++) {


            // Calcolo la data
            if (i == 1 && baseDate.isEqual(LocalDate.parse("1900-01-01"))) {
                baseDate = LocalDate.now();
                check = false;
            }


            // Se il giorno successivo è Sabato o Domenica saltalo
            if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SATURDAY) countDays += 2;
            if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SUNDAY) countDays++;

            // Se la data rilevata inizialmente non è la data di default
            if (check) {
                // Se la data odierna è precedenta all'ultima data inserita nel db allora incrementala poichè
                // la data estratta è già stata calcolata
                if (i == 1 && LocalDate.now().isBefore(baseDate)) countDays++;
                // Se la nuova data è di domenica aggiungi due giorni, se è di sabato aggiungi un giorno
                if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SATURDAY) countDays += 2;
                if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SUNDAY) countDays++;
                // Se la data odierna coincide con la data estatta dal DB ( caso in cui al primo avvio sia satato calcolato 1 solo giorno )
                if (i == 1 && LocalDate.now().isEqual(baseDate)) {
                    // Aggiungi 1 giorno portando così la data al giorno successivo e verifica che questa non sia sabato a domenica
                    countDays++;
                    if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SATURDAY) countDays += 2;
                    if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SUNDAY) countDays++;
                }
            }
            setDate = baseDate.plusDays(countDays);


            // Mappo il turno da inserire
            Turno turno = new Turno();
            turno.setIndex_t(unique_id);
            StringBuilder data = new StringBuilder();
            data.append(setDate.getYear());
            data.append("-");
            if (setDate.getMonthValue() >= 1 && setDate.getMonthValue() <= 9)
                data.append("0" + setDate.getMonthValue());
            else
                data.append(setDate.getMonthValue());
            data.append("-");
            if (setDate.getDayOfMonth() >= 1 && setDate.getDayOfMonth() <= 9)
                data.append("0" + setDate.getDayOfMonth());
            else
                data.append(+setDate.getDayOfMonth());
            turno.setData(data.toString());


            turno.setIndex_g(indexg);
            indexg++;

            if(indexg == nDipendenti)
                indexg= 0;


            // Verifico se devo avanzare con la data, poichè il numero max di dipendenti è 4 per ogni giorno
            // ogni qualvolta elaboro 4 dipendenti passo al giorno dopo.


            if (i % nDipendenti == 0) {
                countDays++;

                unique_id = ThreadLocalRandom.current().nextInt(0, 1000);

                List<Integer> test = repository.queryGenerate();

                while (test.contains(unique_id))
                    unique_id = ThreadLocalRandom.current().nextInt(0, 1000);

                idGiornata.clear();
            }


            int randomIndex = ThreadLocalRandom.current().nextInt(0, idDependent.size());



            while(idGiornata.contains(idDependent.get(randomIndex)))
                randomIndex = ThreadLocalRandom.current().nextInt(0, idDependent.size());

            List<String> queryCheck = repository.queryCheck(turno.getIndex_t());

            while(queryCheck.contains(idDependent.get(randomIndex)))
                randomIndex = ThreadLocalRandom.current().nextInt(0, idDependent.size());

           turno.setIndex_d(idDependent.get(randomIndex));


            /// Rimuovo dalla lista l'ID dell'utente in modo che non venga ripescato

            idDependent.remove(randomIndex);

            /// Verifico che la lista non debba essere ricaricata
            if (idDependent.isEmpty()) {
                idDependent.addAll(feignDependent.GetAllIndex());
                idDependent.removeAll(idGiornata);
            }

            /// Inserisco il turno del DB

            Session currentSession = entityManager.unwrap(Session.class);

            currentSession.beginTransaction();
            currentSession.save(turno);
            currentSession.getTransaction().commit();

        }

            return "Completed";
    }


    public String getMaxDate() {

        repository.queryMax();

        if (repository.queryMax() == null)
            return "1900-01-01";


        List<Turno> turno = repository.queryTurno();
        return turno.get(0).getData();
    }




}

package com.example.turni.service;

import com.example.turni.client.FeignDependent;
import com.example.turni.pojo.Response;
import com.example.turni.pojo.Turno;
import com.example.turni.repository.TurnoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HTMLService {

    @Autowired
    FeignDependent feignDependent;

    @Autowired
    RealizzaTurni turnMethod;

    @Autowired
    TurnoRepo repository;

    public List<Response> getMultipleResponse(String dataInizio, String dataFine) {
        List<Response> list = new ArrayList<>();
        List<String> idDep = repository.queryBetween(dataInizio, dataFine);

        for (String index_d : idDep)
            list.add(feignDependent.getResponse(index_d));

        return list;
    }

    public List<String> getData(String dataInizio, String dataFine) {

        return repository.queryData(dataInizio, dataFine);
    }


    public List<Response> getResponse(String data) {
        List<Response> list = new ArrayList<>();
        List<String> idDep = repository.queryGetData(data);

        for (String index_d : idDep)
            list.add(feignDependent.getResponse(index_d));

        return list;
    }

    public List<Response> getList(String data) {

        List<Response> result = new ArrayList<>();

        if (!data.isEmpty())
            result.addAll(getResponse(data));
        else {
            StringBuilder today = new StringBuilder();

            today.append(LocalDate.now().getYear());
            today.append("-");
            today.append(LocalDate.now().getMonthValue());
            today.append("-");
            today.append(LocalDate.now().getDayOfMonth());
            result.addAll(getResponse(today.toString()));

        }
        return result;
    }


    public List<Response> getDependents() {

        return feignDependent.getDependents();
    }


    public List<Response> findByKeyword(String keyword) {
        return feignDependent.findByKeyword(keyword);
    }

    public void deleteDependent(String index_d) {
        feignDependent.deleteDependent(index_d);
    }

    public void postDependent(@RequestBody Response response) {
        feignDependent.postDependent(response);
    }

    public String creaTurni(int nGiorni, int nDipendenti) {
        return turnMethod.calcolaTurni(nGiorni, nDipendenti);
    }

    public List<Turno> showAllTurns() {
        return repository.findAll();
    }

    public void deleteTurno(String data) {
        repository.queryDelete(data);
    }


    public void updateTurno(String index_d, int index_g) {

        repository.queryUpdate(index_d, index_g);

    }

    public String selectData(String data) {

        if (data.isEmpty())
            data = (String.valueOf(LocalDate.now()));

        else
            data = repository.selectData(data);

        return data;
    }

    public void updateDependent(String nome, String cognome, int index_g) {

        String index_d = feignDependent.queryIndexD(nome, cognome);

        //Integer index_g = repository.selectQueryG(index_d,data);

        updateTurno(index_d, index_g);


    }

    public List<Integer> selectDays(String data) {

        if (data.isEmpty())
            data = (String.valueOf(LocalDate.now()));
        return repository.selectQueryG(data);
    }


}

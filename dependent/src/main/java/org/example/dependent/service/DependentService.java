package org.example.dependent.service;


import org.example.dependent.pojo.Dependent;
import org.example.dependent.pojo.Response;
import org.example.dependent.repository.DependentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DependentService {

    @Autowired
    DependentRepo repository;

    public List<Dependent> getDependents() {
        return repository.findAll();
    }

    public List<String> getAllIndex() {

        return repository.queryIndex();
        
    }

    public void postDependent(Dependent dependent) {

        int [] array = new int[9];
        int randomChar = ThreadLocalRandom.current().nextInt(0, array.length);

        String generatedString = dependent.generateId();

        List<String> allIndex = repository.queryGenerate();

        if(allIndex.contains(generatedString)){
           generatedString+=randomChar;
        }
        dependent.setIndex_d(String.valueOf(generatedString));
        repository.save(dependent);
    }

    public void deleteDependentById(String index_d) {

        repository.queryDelete(index_d);
    }


    public String getStringDependent(String index_d) {

        List<Dependent> dipendente = repository.querySearch(index_d);

        StringBuilder uscita = new StringBuilder();

        uscita.append(" - Matricola : " + dipendente.get(0).getIndex_d());
        uscita.append(" - Cognome : " + dipendente.get(0).getCognome());
        uscita.append(" - Nome : " + dipendente.get(0).getNome());

        return uscita.toString();
    }

    public Response getResponse(String index_d) {

            List<Dependent> dependents = repository.querySearch(index_d);

       return new Response(dependents.get(0).getIndex_d(),
                dependents.get(0).getCognome(),
                dependents.get(0).getNome());
    }

    public List<Dependent> findByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }

}

package org.example.dependent.Service;



import org.example.dependent.Pojo.Dependent;
import org.example.dependent.Repository.DependentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class DependentService {

    @Autowired
    DependentRepo repository;

    public Dependent getDependent(int id) {
        return repository.getOne(id);
    }


    public List<Dependent> getDependents() {
        return repository.findAll();
    }

    public List<Integer> getAllIds() {
        List<Integer> listId = new ArrayList<>();

        for (int i = 1; i < repository.findAll().size() + 1; i++) {

            listId.add(i);
        }
        return listId;

    }

    public void postDependent(Dependent dependent) {

        repository.save(dependent);
    }

    public void deleteDependentById(int id) {

        repository.deleteById(id);
    }

    public StringBuilder getStringDependent(int id){

        StringBuilder string = new StringBuilder();

            return string.append(repository.findById(id));



    }
}

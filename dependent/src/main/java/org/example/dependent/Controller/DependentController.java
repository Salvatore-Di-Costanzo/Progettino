package org.example.dependent.Controller;


import lombok.Getter;
import org.example.dependent.Pojo.Dependent;
import org.example.dependent.Service.DependentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DependentController {

    @Autowired
    DependentService service;

    @GetMapping("/getDependent/{id}")
    public Dependent getDependent(@PathVariable int id) {
        return service.getDependent(id);
    }

    @GetMapping("/getDependents")
    public List<Dependent> getDependents() {
        return service.getDependents();
    }

    @GetMapping("/getAllIds")
    public List<Integer> getAllIds() {
        return service.getAllIds();
    }

    @PostMapping("/postDependent")
    public void postDependent(@RequestBody Dependent dependent) {
        service.postDependent(dependent);
    }

    @GetMapping("/deleteDependent/{id}")
    public void deleteDependent(@PathVariable int id) {
        service.deleteDependentById(id);
    }

    @GetMapping("/getStringDependent/{id}")
    public String getArrayIds(@PathVariable String id) {
        return service.getStringDependent(Integer.parseInt(id));
    }

}

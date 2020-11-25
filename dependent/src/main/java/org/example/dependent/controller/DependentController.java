package org.example.dependent.controller;


import org.example.dependent.pojo.Dependent;
import org.example.dependent.pojo.Response;
import org.example.dependent.service.DependentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DependentController {

    @Autowired
    DependentService service;

    @GetMapping("/getAllIndex")
    public List<String> GetAllIndex() {
        return service.getAllIndex();
    }

    @GetMapping("/getDependents")
    public List<Dependent> getDependents() {
        return service.getDependents();
    }

    @GetMapping("/getStringDependent/{index_d}")
    public String getStringDependent(@PathVariable String index_d) {
        return service.getStringDependent(index_d);
    }

    @GetMapping("/getResponse/{index_d}")
    public Response getResponse(@PathVariable String index_d) {
        return service.getResponse(index_d);
    }

    @PostMapping("/postDependent")
    public void postDependent(@RequestBody Dependent dependent) {
        service.postDependent(dependent);
    }

    @GetMapping("/deleteDependent/{index_d}")
    public void deleteDependent(@PathVariable String index_d) {
        service.deleteDependentById(index_d);
    }


    @GetMapping("/findDipendente")
    public List<Dependent> findByKeyword(String keyword) {
        return service.findByKeyword(keyword);
    }

}

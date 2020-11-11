package com.example.turni.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "dependent-service",url ="http://localhost:8080")
public interface FeignDependent {

    @GetMapping("/getAllIds")
    public List<Integer> getIds();

    @GetMapping("/getStringDependent/{id}")
    public String getDateDependent(@PathVariable String id);
}

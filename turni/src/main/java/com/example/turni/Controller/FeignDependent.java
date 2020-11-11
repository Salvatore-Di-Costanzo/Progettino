package com.example.turni.Controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "dependent-service",url ="http://localhost:8080")
public interface FeignDependent {

    @GetMapping("/getAllIds")
    public List<Integer> getIds();

    @PostMapping("/getDependent/{id}")
    public String getDateDependent(@RequestBody int id);
}

package com.example.turni.Controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "DEPENDENT-SERVICE",url ="http://localhost:8080")
public interface FeignDependent {

    @GetMapping("/getAllIds")
    public List<Integer> getIds();
}

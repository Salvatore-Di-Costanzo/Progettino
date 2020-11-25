package com.example.turni.client;


import com.example.turni.pojo.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Service
@FeignClient(name = "dependent-service",url ="http://localhost:8080")
public interface FeignDependent {

    @GetMapping("/getAllIndex")
    public List<String> GetAllIndex();

    @GetMapping("/getDependents")
    public List<Response> getDependents();

    @GetMapping("/getStringDependent/{index_d}")
    public String getDateDependent(@PathVariable String index_d);

    @GetMapping("/getResponse/{index_d}")
    public Response getResponse(@PathVariable String index_d);

    @PostMapping("/postDependent")
    public void postDependent(@RequestBody Response response);

    @GetMapping("/deleteDependent/{index_d}")
    public Response deleteDependent(@PathVariable String index_d);

    @GetMapping("/findDipendente")
    public List<Response> findByKeyword(@RequestParam String keyword);





}

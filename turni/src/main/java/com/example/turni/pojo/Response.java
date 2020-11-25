package com.example.turni.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Response {

    private String index_d;

    private String cognome;

    private String nome;

    private Float salary;

    private String data;

    Response(String index_d, String cognome, String nome , Float salary,String data) {
        this.index_d = index_d;
        this.cognome = cognome;
        this.nome = nome;
        this.salary=salary;
        this.data=data;
    }

    public Response(String index_d, String cognome, String nome) {
        this.index_d = index_d;
        this.cognome = cognome;
        this.nome = nome;
    }
}


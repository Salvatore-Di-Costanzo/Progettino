package org.example.dependent.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {

    private String index_d;

    private String cognome;

    private String nome;


    public Response(String index_d, String cognome, String nome) {
        this.index_d = index_d;
        this.cognome = cognome;
        this.nome = nome;

    }

}


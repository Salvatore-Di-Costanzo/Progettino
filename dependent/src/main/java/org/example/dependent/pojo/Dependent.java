package org.example.dependent.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "dependent")
@NoArgsConstructor
@Data
public class Dependent {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "index_d")
    private String index_d;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "nome")
    private String nome;

    @Column(name = "salary")
    private Float salary;


    public Dependent(String nome, String cognome, Float salary, String index_d) {
        this.nome = nome;
        this.cognome = cognome;
        this.salary = salary;
        this.index_d = index_d;

    }

    public Dependent(String index_d, String cognome, String nome) {
        this.index_d = index_d;
        this.cognome = cognome;
        this.nome = nome;
    }

    public String generateId() {
        String nome1 = getNome();
        String cognome1 = getCognome();

        int sumNome = 0;
        int sumCognome = 0;

        for (int i = 0; i < nome1.length(); i++) {

            int value = nome1.charAt(i);
            sumNome += value;

        }
        for (int i = 0; i < cognome1.length(); i++) {

            int value = cognome1.charAt(i);
            sumCognome += value;

        }
        int somma = sumNome + sumCognome;
        String n = String.valueOf(nome1.charAt(0));
        String c = String.valueOf(cognome1.charAt(0));

        return n + c + somma;
    }

}

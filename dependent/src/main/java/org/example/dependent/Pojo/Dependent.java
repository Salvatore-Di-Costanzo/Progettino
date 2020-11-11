package org.example.dependent.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Dependent  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    public Integer id;

    @Column(name ="Nome")
    public String nome;

    @Column(name = "Cognome")
    public String cognome;

    @Column(name = "Salary")
    public Float salary;

    public Dependent(String nome, String cognome, Float salary) {
        this.nome = nome;
        this.cognome = cognome;
        this.salary = salary;
    }

}

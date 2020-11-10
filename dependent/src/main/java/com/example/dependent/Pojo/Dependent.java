package com.example.dependent.Pojo;

import javax.persistence.*;

@Entity
public class Dependent  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    public long id;

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

    public Dependent() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Dependent{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", salary=" + salary +
                '}';
    }
}

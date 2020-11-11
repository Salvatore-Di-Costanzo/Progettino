package com.example.turni.Pojo;


import com.example.turni.Controller.FeignDependent;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Turno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "Data")
    private String date;

    @Column(name = "id_dependent")
    private Integer id_dependent;

    public Turno() {
        
    }

    public Turno(String date, Integer id_dependent) {
        this.date = date;
        this.id_dependent = id_dependent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId_dependent() {
        return id_dependent;
    }

    public void setId_dependent(Integer id_dependent) {
        this.id_dependent = id_dependent;
    }

    @Override
    public String toString() {
        return "Turni{" +
                "id=" + id +
                ", date=" + date +
                ", id_dependent=" + id_dependent +
                '}';
    }
}

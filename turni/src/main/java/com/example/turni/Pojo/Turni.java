package com.example.turni.Pojo;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Turni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "Data")
    private Date date;

    @Column(name = "id_dip")
    private Integer id_dip;

    public Turni(Date date, Integer id_dip) {
        this.date = date;
        this.id_dip = id_dip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId_dip() {
        return id_dip;
    }

    public void setId_dip(Integer id_dip) {
        this.id_dip = id_dip;
    }

    @Override
    public String toString() {
        return "Turni{" +
                "id=" + id +
                ", date=" + date +
                ", id_dip=" + id_dip +
                '}';
    }
}

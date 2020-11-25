package com.example.turni.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="turno")
@Data
@NoArgsConstructor
public class Turno{

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "index_t")
    private int index_t;

    @Column(name = "data")
    private String data;

    @Column(name = "index_d")
    private String index_d;

    @Column(name = "index_g")
    private int index_g;

    public Turno(int index_t, String data, String index_d, int index_g) {
        this.index_t = index_t;
        this.data = data;
        this.index_d = index_d;
        this.index_g = index_g;
    }
}

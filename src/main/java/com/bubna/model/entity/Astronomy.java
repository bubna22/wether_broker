package com.bubna.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "astronomy")
@JsonIgnoreProperties
public class Astronomy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "astro_id")
    private Integer id;
    @Column(name = "astro_sunrise")
    private Date sunrise;//hh:mm 12 hours
    @Column(name = "astro_sunset")
    private Date sunset;//hh:mm 12 hours
}

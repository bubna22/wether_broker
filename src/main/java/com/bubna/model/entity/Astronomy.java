package com.bubna.model.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "astronomy_data")
public class Astronomy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "astro_id")
    private Integer id;
    @Column(name = "astro_sunrise")
    private Date sunrise;//hh:mm 12 hours
    @Column(name = "astro_sunset")
    private Date sunset;//hh:mm 12 hours
}

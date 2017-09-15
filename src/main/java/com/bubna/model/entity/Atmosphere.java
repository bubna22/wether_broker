package com.bubna.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "atmospheres")
public class Atmosphere implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atmo_id")
    private Integer id;
    @Column(name = "atmo_humidity")
    private Integer humidity;
    @Column(name = "atmo_pressure")
    private Float pressure;
    @Column(name = "atmo_rising")
    private Integer rising;
    @Column(name = "atmo_visibility")
    private Float visibility;
}

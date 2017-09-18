package com.bubna.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "units")
@JsonIgnoreProperties
public class Units implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "units_id")
    private Integer id;
    @Column(name = "units_distance")
    private String distance;
    @Column(name = "units_pressure")
    private String pressure;
    @Column(name = "units_speed")
    private String speed;
    @Column(name = "units_temperature")
    private String temperature;
}

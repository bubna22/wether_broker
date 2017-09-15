package com.bubna.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "units")
public class Units {
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

package com.bubna.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "winds")
public class Wind implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wind_id")
    private Integer id;
    @Column(name = "wind_chill")
    private Integer chill;
    @Column(name = "wind_direction")
    private Integer direction;
    @Column(name = "wind_speed")
    private Integer speed;
}

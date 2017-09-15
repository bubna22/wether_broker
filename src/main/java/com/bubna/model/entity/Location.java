package com.bubna.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loc_id")
    private Integer id;
    @Column(name = "loc_city")
    private String city;
    @Column(name = "loc_country")
    private String country;
    @Column(name = "loc_region")
    private String region;
}

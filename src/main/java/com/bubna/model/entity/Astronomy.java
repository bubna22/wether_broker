package com.bubna.model.entity;

import com.bubna.model.entity.json.Custom2JsonDateDeserializer;
import com.bubna.model.entity.json.CustomJsonDateDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "astronomy")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Astronomy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "astro_id")
    private Integer id;
    @Column(name = "astro_sunrise")
    @JsonDeserialize(using = Custom2JsonDateDeserializer.class)
    private Date sunrise;//hh:mm 12 hours
    @JsonDeserialize(using = Custom2JsonDateDeserializer.class)
    @Column(name = "astro_sunset")
    private Date sunset;//hh:mm 12 hours

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }
}

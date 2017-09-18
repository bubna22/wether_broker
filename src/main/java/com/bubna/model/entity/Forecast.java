package com.bubna.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "forecasts")
@JsonIgnoreProperties
public class Forecast implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forecast_id")
    private Integer code;
    @Column(name = "forecast_date")
    private Date date;
    @Column(name = "forecast_day")
    private String day;
    @Column(name = "forecast_high")
    private Integer high;
    @Column(name = "forecast_low")
    private Integer low;
    @Column(name = "forecast_text")
    private String text;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "forecast_item")
    private Item item;
}

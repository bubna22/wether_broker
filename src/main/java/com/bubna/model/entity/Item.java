package com.bubna.model.entity;

import com.bubna.model.entity.json.Custom1JsonDateDeserializer;
import com.bubna.model.entity.json.CustomJsonDateDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "items")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;
    @Column(name = "item_title")
    private String title;
    @Column(name = "item_lat")
    private Double latitude;
    @Column(name = "item_long")
    private Double longitude;
    @Column(name = "item_link")
    private String link;
    @Column(name = "item_pub_date")
    @JsonDeserialize(using = Custom1JsonDateDeserializer.class)
    private Date pubDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_condition")
    private Condition condition;
    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Forecast> forecasts;
    @Column(name = "item_desc")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Set<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(Set<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

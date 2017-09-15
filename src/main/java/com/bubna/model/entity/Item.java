package com.bubna.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "items")
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
    private Date pubDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_condition")
    private Condition condition;
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<Forecast> forecasts;
    @Column(name = "item_desc")
    private String description;
}

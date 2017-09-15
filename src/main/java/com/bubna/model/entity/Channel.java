package com.bubna.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "channels")
public class Channel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_units")
    private Units units;
    @Column(name = "channel_title")
    private String title;
    @Column(name = "channel_link")
    private String link;
    @Column(name = "channel_desc")
    private String description;
    @Column(name = "channel_lang")
    private String language;
    @Column(name = "channel_last_build")
    private Date lastBuildDate;
    @Column(name = "channel_ttl")
    private Integer ttl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_location")
    private Location location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_wind")
    private Wind wind;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_atmosphere")
    private Atmosphere atmosphere;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_astronomy")
    private Astronomy astronomy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_image")
    private Image image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_item")
    private Item item;
}

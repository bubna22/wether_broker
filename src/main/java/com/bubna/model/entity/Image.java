package com.bubna.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "images")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer id;
    @Column(name = "image_title")
    private String title;
    @Column(name = "image_width")
    private Integer width;
    @Column(name = "image_height")
    private Integer height;
    @Column(name = "image_link")
    private String link;
    @Column(name = "image_url")
    private String url;
}

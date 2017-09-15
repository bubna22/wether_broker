package com.bubna.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "queries")
public class Query implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "query_id")
    private Integer id;
    @Column(name = "query_count")
    private Integer count;
    @Column(name = "query_created")
    private Date created;
    @Column(name = "query_lang")
    private String lang;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "query_channel")
    private Channel channel;
}

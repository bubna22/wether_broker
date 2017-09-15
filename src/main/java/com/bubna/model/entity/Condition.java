package com.bubna.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "conditions")
public class Condition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id")
    private Integer id;
    @Column(name = "condition_code")
    private Integer code;
    @Column(name = "condition_date")
    private Date date;
    @Column(name = "condition_temp")
    private Integer temp;
    @Column(name = "condition_text")
    private String text;
}

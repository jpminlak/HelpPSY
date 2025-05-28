package com.cai.helppsy.map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Mapmarker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;

    @Column(length = 200)
    private String content;

    private String lon;
    private String lat;
    private String regX;
    private String regY;
}

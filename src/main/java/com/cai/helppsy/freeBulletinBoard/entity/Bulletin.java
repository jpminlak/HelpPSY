package com.cai.helppsy.freeBulletinBoard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Bulletin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(length = 30)
    private String title;

    @Column(length = 1200)
    private String content;

    @Column(length = 12)
    private String writer;

    @Column(length = 9)
    private Integer views;

    private LocalDateTime createDate;
}

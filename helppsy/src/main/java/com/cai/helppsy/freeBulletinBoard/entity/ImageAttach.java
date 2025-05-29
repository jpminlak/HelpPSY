package com.cai.helppsy.freeBulletinBoard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class ImageAttach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imgNum;

    private Integer no;

    @Column(length = 100)
    private String imgName;

    private LocalDateTime createDate;
}

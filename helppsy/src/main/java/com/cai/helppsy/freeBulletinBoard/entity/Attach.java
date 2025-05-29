package com.cai.helppsy.freeBulletinBoard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Attach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attach_num;

    private Integer no;

    @Column(length = 100)
    private String fileName;

    private LocalDateTime createDate;
}

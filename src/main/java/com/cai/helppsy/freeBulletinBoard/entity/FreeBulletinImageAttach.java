package com.cai.helppsy.freeBulletinBoard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class FreeBulletinImageAttach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(length = 100)
    private String imgName;

    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "fk_no")
    private FreeBulletin freeBulletin;
}

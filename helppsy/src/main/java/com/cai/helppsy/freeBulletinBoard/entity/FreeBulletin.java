package com.cai.helppsy.freeBulletinBoard.entity;

import com.cai.helppsy.likes.entity.FreeBulletinLike;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class FreeBulletin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(length = 30)
    private String title;

    // 자료형이 MEDIUMTEXT로 된다. 용량 약 16MB 제한.
    @Column(length = 200000)
    private String content;

    @Column(length = 15)
    private String writer;

    @Column(length = 9)
    private Integer views;

    @Column(length = 9)
    private int likes;

    private LocalDateTime createDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freeBulletin", orphanRemoval = true)
    private List<FreeBulletinAttach> freeBulletinAttaches;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freeBulletin", orphanRemoval = true)
    private List<FreeBulletinComment> freeBulletinComments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freeBulletin", orphanRemoval = true)
    private List<FreeBulletinLike> freeBulletinLikesList;
}

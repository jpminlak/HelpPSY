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
    @Lob    // 이 필드는 큰 데이터를 저장할 수 있다라는걸 표현하는 어노테이션
    @Column(columnDefinition = "LONGTEXT")
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

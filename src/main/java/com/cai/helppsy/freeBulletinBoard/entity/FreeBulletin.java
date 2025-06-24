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

    // 속성값의 길이가 longtext가 된다. 용량 약 4GB 까지 제한.
    @Column(columnDefinition = "longtext")
    private String content;

    // 속성값의 길이가 longtext가 된다. 용량 약 16MB 까지 제한.
    @Column(columnDefinition = "mediumtext")
    private String thumbnail;

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

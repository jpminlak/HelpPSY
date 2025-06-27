package com.cai.helppsy.freeBulletinBoard.entity;

import com.cai.helppsy.likes.entity.FreeBulletinCommentLike;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class FreeBulletinComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 10)
    private String type;

    @Column(length = 20)
    private String userId;

    @Column(length = 400)
    private String content;

    @Column(length = 9)
    private int likes;

    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "fk_no")
    private FreeBulletin freeBulletin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freeBulletinComment", orphanRemoval = true)
    private List<FreeBulletinCommentLike> freeBulletinCommentLikeList;
}

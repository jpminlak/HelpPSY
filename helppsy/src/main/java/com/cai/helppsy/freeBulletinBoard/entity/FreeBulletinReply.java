package com.cai.helppsy.freeBulletinBoard.entity;


import com.cai.helppsy.likes.entity.FreeBulletinReplyLike;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class FreeBulletinReply {
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
    @JoinColumn(name = "fkNo")
    private FreeBulletinComment freeBulletinComment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freeBulletinReply", orphanRemoval = true)
    private List<FreeBulletinReplyLike> freeBulletinReplyLikeList;
}

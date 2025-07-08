package com.cai.helppsy.likes.entity;


import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinComment;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class FreeBulletinCommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 16)
    private String type;

    @Column(length = 20)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "fk_no")
    private FreeBulletinComment freeBulletinComment;
}

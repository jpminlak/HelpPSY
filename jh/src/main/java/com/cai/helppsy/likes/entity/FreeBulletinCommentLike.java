package com.cai.helppsy.likes.entity;


import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinComment;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class FreeBulletinCommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 16)
    private String type;

    @Column(length = 15)
    private String userName;

    @ManyToOne
    @JoinColumn(name = "fk_no")
    private FreeBulletinComment freeBulletinComment;
}

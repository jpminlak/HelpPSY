package com.cai.helppsy.likes.entity;


import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import jakarta.persistence.*;


@Entity
@Data
public class FreeBulletinLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 16)
    private String type;

    @Column(length = 15)
    private String userName;

    @ManyToOne
    @JoinColumn(name = "fk_no")
    private FreeBulletin freeBulletin;

}

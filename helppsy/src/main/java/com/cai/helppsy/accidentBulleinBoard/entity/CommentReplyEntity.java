package com.cai.helppsy.accidentBulleinBoard.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommentReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 역할 숫자 1씩 증감
    private Integer id; // 저장 번호

    @Column(length = 200)
    private String comment; // 내용

    @Column(length = 15)
    private String alias; // 로그인한 사용자 별명

    //  등록 글과의 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_entity_id", nullable = false)
    private CommentEntity commentEntity;

}

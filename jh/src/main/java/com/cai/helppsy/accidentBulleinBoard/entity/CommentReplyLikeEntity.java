package com.cai.helppsy.accidentBulleinBoard.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommentReplyLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 역할 숫자 1씩 증감
    private Integer id; // 저장 번호

    @Column(length = 15)
    private String type; // 해당 게시물 타입

    @Column(length = 15)
    private String alias; // 로그인한 사용자 별명

    @Column(length = 15)
    private int liked; // 좋아요 현재 상태

    //  댓글과 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_reply_entity_id", nullable = false)
    private CommentReplyEntity commentReplyEntity;
}

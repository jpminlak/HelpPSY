package com.cai.helppsy.accidentBulleinBoard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class CommentEntity {

    @Id // 기본키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 역할 숫자 1씩 증감
    private Integer id; // 저장 번호

    @Column(length = 200)
    private String comment; // 내용

    @Column(length = 10)
    private String alias; // 사용자 별명

    //  등록 글과의 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_entity_id", nullable = false)
    private RegistrationEntity registrationEntity;

    // 대댓글에 1:N 관계 성립 (기본키)
    @OneToMany(mappedBy = "commentEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentReplyEntity> reply;

}

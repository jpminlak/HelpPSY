package com.cai.helppsy.accidentBulleinBoard.entity;

import com.cai.helppsy.memberManager.SignupEntity;
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

    // 댓글 좋아요 1:N 관계 성립 (기본키)
    @OneToMany(mappedBy = "commentEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLikeEntity> CommentLikeEntity;

    // 대댓글에 1:N 관계 성립 (기본키)
    @OneToMany(mappedBy = "commentEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentReplyEntity> reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signup_entity_id")
    // Join되는거 service쪽 fileEntity.setRegistrationEntity(registrationEntity);에서 연결
    private SignupEntity signupEntity;
}

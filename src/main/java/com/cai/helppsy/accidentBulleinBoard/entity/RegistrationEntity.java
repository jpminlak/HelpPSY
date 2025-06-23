package com.cai.helppsy.accidentBulleinBoard.entity;


import com.cai.helppsy.memberManager.SignupEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 역할 숫자 1씩 증감
    private Integer id; // 저장 번호

    @Column(length = 50)
    private String title; // 제목

    @Column(columnDefinition = "TEXT")
    private String Preview; // 미리보기 글

    @Column(columnDefinition = "MEDIUMTEXT")
    private String MainImg; // 썸네일 이미지

    @Column(length = 15)
    private String alias; // 로그인한 사용자 별명

    @Column(length = 30)
    private String accident; // 제보카테고리

    @Column(length = 30)
    private String distinction; // 제보 분류

    @Column(length = 50)
    private String region; // 제보지역

    @Column(length = 15)
    private String type; // 제보상세위치

    @Column(columnDefinition = "LONGTEXT")
    private String content; // 내용

    @Column(length = 30)
    private String latitude; // 지도(위도)

    @Column(length = 30)
    private String longitude; // 지도(경도)

    @Column(nullable = false) // nullable = false : null 방지
    private Integer postViews = 0; // 조회수

    @CreationTimestamp
    private LocalDateTime createDate; // 로컬 데이터 시간 표기

    @UpdateTimestamp
    private LocalDateTime modifiedDate; // 글 수정 시간

    @Column(columnDefinition = "MEDIUMTEXT")
    private String profileImage; // 회원 프로필 이미지

    // 1:N 관계에서 사용
    // 파일 리스트
    @OneToMany(mappedBy = "registrationEntity",cascade = CascadeType.REMOVE, orphanRemoval = true)
    // mappedBy=참조엔티티속성명 , cascade = CascadeType.REMOVE = 질문을 삭제하면 답변도 같이 삭제되게끔 하는 코드
    private List<RegistrationFileEntity> filelist;

    // 댓글 리스트
    @OneToMany(mappedBy = "registrationEntity",cascade = CascadeType.REMOVE, orphanRemoval = true)
    // mappedBy=참조엔티티속성명 , cascade = CascadeType.REMOVE = 질문을 삭제하면 답변도 같이 삭제되게끔 하는 코드
    private List<CommentEntity> CommentEntity;

    // 좋아요 리스트
    @OneToMany(mappedBy = "registrationEntity",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RegistrationLikeEntity> likeEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signup_entity_id")
    // Join되는거 service쪽 fileEntity.setRegistrationEntity(registrationEntity);에서 연결
    private SignupEntity signupEntity;
}

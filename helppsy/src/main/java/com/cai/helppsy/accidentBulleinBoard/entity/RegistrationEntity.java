package com.cai.helppsy.accidentBulleinBoard.entity;


import com.cai.helppsy.accidentBulleinBoard.serviece.CommentService;
import com.cai.helppsy.main.entity.SinupEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(length = 15)
    private String alias; // 로그인한 사용자 별명

    @Column(length = 30)
    private String accident; // 사고분류

    @Column(length = 15)
    private String region; // 지역

    @Column(length = 15)
    private String rating; // 차등급(크기)

    @Column(length = 15)
    private String type; // 차종류

    @CreationTimestamp
    private LocalDateTime createDate; // 로컬 데이터 시간 표기

    // 1:N 관계에서 사용
    @OneToMany(mappedBy = "registrationEntity",cascade = CascadeType.REMOVE, orphanRemoval = true)
    // mappedBy=참조엔티티속성명 , cascade = CascadeType.REMOVE = 질문을 삭제하면 답변도 같이 삭제되게끔 하는 코드
    private List<RegistrationFileEntity> filelist;

    @OneToMany(mappedBy = "registrationEntity",cascade = CascadeType.REMOVE, orphanRemoval = true)
    // mappedBy=참조엔티티속성명 , cascade = CascadeType.REMOVE = 질문을 삭제하면 답변도 같이 삭제되게끔 하는 코드
    private List<CommentEntity> CommentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sinup_entity_id")
    // Join되는거 service쪽 fileEntity.setRegistrationEntity(registrationEntity);에서 연결
    private SinupEntity sinupentity;
}

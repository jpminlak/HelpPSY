package com.cai.helppsy.accidentBulleinBoard.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RegistrationFileEntity {

    @Id // 외래키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 역할 숫자 1씩 증감
    private Integer id; // 저장 번호

    @Column(columnDefinition = "Text")
    private String filename; // 파일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_entity_id")
    // Join되는거 service쪽 fileEntity.setRegistrationEntity(registrationEntity);에서 연결
    private RegistrationEntity registrationEntity;
}

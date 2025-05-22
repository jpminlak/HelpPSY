package com.example.demo.accident;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class RegistrationEntity {

    @Id // 외래키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 역할 숫자 1씩 증감
    private Integer id; // 저장 번호

    @Column(length = 50)
    private String title; // 제목

    @Column(length = 30)
    private String accident; // 사고

    @Column(length = 15)
    private String region; // 지역

    @Column(length = 15)
    private String rating; // 차등급

    @Column(length = 15)
    private String type; // 차종류

    @CreationTimestamp
    private LocalDateTime createDate; // 로컬 데이터 시간 표기
}

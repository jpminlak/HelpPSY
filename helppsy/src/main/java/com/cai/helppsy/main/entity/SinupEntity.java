package com.cai.helppsy.main.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SinupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 역할 숫자 1씩 증감
    private Integer id; // 저장 번호

    @Column(length = 20)
    private String userId; // 아이디

    @Column(length = 20)
    private String userPass; // 비밀번호

    @Column(length = 15)
    private String alias; // 별명

    @Column(length = 1000)
    private String intro;   //소개글




}

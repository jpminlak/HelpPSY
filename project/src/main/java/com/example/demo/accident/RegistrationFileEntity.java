package com.example.demo.accident;


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


}

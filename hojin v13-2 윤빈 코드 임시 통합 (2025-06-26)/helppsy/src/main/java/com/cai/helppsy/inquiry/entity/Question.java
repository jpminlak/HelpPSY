package com.cai.helppsy.inquiry.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String writer; // 기존 필드 유지 (작성 당시의 별명)

    @Column(length = 20)
    private String writerUserId;

    @Column(length = 30)
    private String subject;

    @Column(length = 400)
    private String content;

    @Column(length = 200)
    private String file;

    @Column(columnDefinition = "TEXT")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

}
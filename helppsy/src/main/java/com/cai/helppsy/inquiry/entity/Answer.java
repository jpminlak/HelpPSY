package com.cai.helppsy.inquiry.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String respondent;

    @Column(length = 50)
    private String title;

    @Column(length = 200)
    private String content2;

    @Column(columnDefinition = "TEXT")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "question_id")   //name = question_id 이면 join?
    private Question question;
}

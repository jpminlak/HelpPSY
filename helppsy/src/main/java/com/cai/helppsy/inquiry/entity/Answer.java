// Answer.java
package com.cai.helppsy.inquiry.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(length = 20)
    private String respondentUserId; // <-- 이 필드가 있는지 확인합니다.

    @Column(length = 50)
    private String title;

    @Column(length = 200)
    private String content2;

    @Column(columnDefinition = "TEXT")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
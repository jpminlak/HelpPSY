package com.cai.helppsy.inquiry;

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

//    @ManyToOne
//    private Question question;
}

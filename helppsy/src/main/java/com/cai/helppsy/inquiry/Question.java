package com.cai.helppsy.inquiry;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String writer;

    @Column(length = 30)
    private String subject;

    @Column(length = 200)
    private String content;

    @Column(columnDefinition = "TEXT")
    private LocalDateTime createDate;

//    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
//    private List<Answer> answerList;

}

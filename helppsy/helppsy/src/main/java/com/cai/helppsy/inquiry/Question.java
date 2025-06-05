package com.cai.helppsy.inquiry;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    private String writer;

    @Column(length = 30)
    private String subject;

    @Column(length = 400)
    private String content;

    @Column(length = 200)
    private String file;

    @Column(columnDefinition = "TEXT")
    private LocalDateTime createDate;


    @Builder
    public void Posts(String writer, String subject, String content, String file){
        this.writer = writer;
        this.subject = subject;
        this.content = content;
        this.file = file;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

}

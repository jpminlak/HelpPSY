package com.cai.helppsy.note;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Note {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String receiverId;   //수신자
    private String senderId;  //발신자
    private String title;   //제목
    private String content; //내용
    private LocalDateTime sentAt;

    @Column(nullable = true) // null 값 허용
    private Integer relatedQuestionId;  //어떤 쪽지에 대한 답변인지
}
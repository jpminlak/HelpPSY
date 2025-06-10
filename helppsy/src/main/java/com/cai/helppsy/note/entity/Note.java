package com.cai.helppsy.note.entity;

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

}

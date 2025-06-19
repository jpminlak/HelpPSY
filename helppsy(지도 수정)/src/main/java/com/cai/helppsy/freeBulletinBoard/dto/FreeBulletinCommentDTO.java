package com.cai.helppsy.freeBulletinBoard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FreeBulletinCommentDTO {
    private int no;

    private String type;

    private String writer;

    private String content;

    private int likes;

    private LocalDateTime createDate;

    private int fkNo;

    private int isPressedCommentLike;
}

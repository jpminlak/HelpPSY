package com.cai.helppsy.freeBulletinBoard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FreeBulletinDTO {
    private Integer no;

    private String title;

    private String content;

    private String thumbnail;

    private String writer;

    private Integer views;

    private int likes;

    private LocalDateTime createDate;

    private String profileImgName;
}

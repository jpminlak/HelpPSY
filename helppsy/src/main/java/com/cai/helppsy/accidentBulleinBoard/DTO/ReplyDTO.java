package com.cai.helppsy.accidentBulleinBoard.DTO;

import lombok.Getter;

@Getter
public class ReplyDTO {
    private Integer id; // 대댓글 순서번호
    private String comment; // 대댓글 내용
    private String alias; // 사용자 별명
    private Integer CommentId; // 댓글 작성번호

    public ReplyDTO(Integer id, String comment, String alias, Integer CommentId){
        this.id = id;
        this.comment=comment;
        this.alias=alias;
        this.CommentId=CommentId;
    }
}

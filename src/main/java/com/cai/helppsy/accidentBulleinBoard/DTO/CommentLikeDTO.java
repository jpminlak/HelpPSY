package com.cai.helppsy.accidentBulleinBoard.DTO;


import lombok.Getter;

@Getter
public class CommentLikeDTO {

    private Integer id; // 저장 번호
    private String type; // 해당 게시물 타입
    private String alias; // 로그인한 사용자 별명
    private int liked; // 좋아요 현재 상태

    public CommentLikeDTO(Integer id, String type, String alias, int liked){
        this.id=id;
        this.type=type;
        this.alias=alias;
        this.liked=liked;

    }
}

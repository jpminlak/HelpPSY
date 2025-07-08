package com.cai.helppsy.accidentBulleinBoard.DTO;


import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import lombok.Getter;

@Getter
public class CommentDTO {
    private CommentEntity comment;  // 댓글작성자
    private String profileImage;    // 댓글작성자 프로필 이미지

    public CommentDTO(CommentEntity comment, String profileImage){
        this.comment = comment;
        this.profileImage = profileImage;
    }

    public CommentEntity getComment() { return comment; }
    public String getProfileImage() { return profileImage; }
}

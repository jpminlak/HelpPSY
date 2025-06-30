package com.cai.helppsy.accidentBulleinBoard.repository;


import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentReplyRepository extends JpaRepository<CommentReplyEntity,Integer> {

//    static Optional<CommentReplyEntity> findBySignupEntity_Id(Integer id) {
//    }

    // 댓글 ID로 대댓글 목록 조회
    List<CommentReplyEntity> findByCommentEntityId(Integer commentId);

    // 대댓글 수정
    CommentReplyEntity findByIdAndAlias(Integer commentReplyId, String commentReplyAlias);

    // 회원프로필 별명 최신화 시키기
    List<CommentReplyEntity> findBySignupEntity_Id(Integer id); // 회원 프로필 별명 최신화
}

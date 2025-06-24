package com.cai.helppsy.accidentBulleinBoard.repository;


import com.cai.helppsy.accidentBulleinBoard.entity.CommentReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReplyRepository extends JpaRepository<CommentReplyEntity,Integer> {

    // 댓글 ID로 대댓글 목록 조회
    List<CommentReplyEntity> findByCommentEntityId(Integer commentId);
}

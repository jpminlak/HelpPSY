package com.cai.helppsy.accidentBulleinBoard.repository;


import com.cai.helppsy.accidentBulleinBoard.entity.CommentReplyLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentReplyLikeRepository extends JpaRepository<CommentReplyLikeEntity,Integer> {

    // 좋아요 중복 조회
    Optional<CommentReplyLikeEntity> findByAliasAndCommentReplyEntity_Id(String alias,Integer replyId);

    // 삭제
    void deleteByAliasAndCommentReplyEntity_Id(String alias, Integer replyId);

    // 좋아요 총 갯수 표현
    int countByCommentReplyEntity_Id(Integer replyId);
}

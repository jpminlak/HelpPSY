package com.cai.helppsy.accidentBulleinBoard.repository;


import com.cai.helppsy.accidentBulleinBoard.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity,Integer> {

    // 좋아요 중복을 확인하기 위해 entity 기록을 확인
    Optional<CommentLikeEntity> findByAliasAndCommentEntity_Id(String alias, Integer commentId);

    // 기록이 있다면 해당 기록 삭제
    void deleteByAliasAndCommentEntity_Id(String alias, Integer commentId);

    // 좋아요 총 갯수 표현
    int countByCommentEntity_Id(Integer commentId);
}

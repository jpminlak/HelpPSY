package com.cai.helppsy.accidentBulleinBoard.repository;

import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    // 외래키 id로 댓글가져오기
    List<CommentEntity> findByRegistrationEntity_Id(Integer id);

    // 수정할 댓글 찾기
    CommentEntity findByIdAndAlias(Integer commentEditId,String commentEditAlias);

    // 댓글 삭제하기
//    void deleteByIdAndAliasAndRegistrationEntity_Id(Integer id, String alias);
//    void deleteByIdAndAliasAndRegistrationEntity_Id(Integer id, String alias, Integer registration_entity_id);
}

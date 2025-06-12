package com.cai.helppsy.accidentBulleinBoard.repository;


import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationLikeRepository extends JpaRepository<RegistrationLikeEntity,Integer> {
    // 특정 사용자와 게시글에 대한 좋아요 존재 여부 확인
    // id로 조회하기
    Optional<RegistrationLikeEntity> findByAliasAndRegistrationEntityId(
            String alias, Integer registration_entity_id);

    // id로 삭제하기
    void deleteByAliasAndRegistrationEntityId(
            String alias, Integer registration_entity_id);

    // 각 게시물 총 좋아요 갯수 가져오기
    int countByRegistrationEntityId(Integer registration_entity_id);
}

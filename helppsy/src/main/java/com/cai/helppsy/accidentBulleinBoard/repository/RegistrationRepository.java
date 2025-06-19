package com.cai.helppsy.accidentBulleinBoard.repository;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity,Integer> {
    List<RegistrationEntity> findAll(); // 전체출력
    Optional<RegistrationEntity> findById(Integer id); // id로 조회하기
    Optional<RegistrationEntity> findByIdAndAlias(Integer id, String alias); // 게시물 수정
    List<RegistrationEntity> findTop3ByOrderByPostViewsDesc();  // 조회수 상위 3개 추천
    List<RegistrationEntity> findByAlias(String alias);     // 사고게시판 회원별 글 작성 목록
}

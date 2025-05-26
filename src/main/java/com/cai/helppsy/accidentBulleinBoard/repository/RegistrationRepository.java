package com.cai.helppsy.accidentBulleinBoard.repository;


import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity,Integer> {
    List<RegistrationEntity> findAll(); // 전체출력
    Optional<RegistrationEntity> findById(Integer id); // id로 조회하기
}

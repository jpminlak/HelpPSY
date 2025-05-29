package com.cai.helppsy.accidentBulleinBoard.repository;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RegistrationFileRepository extends JpaRepository<RegistrationFileEntity,Integer> {
    List<RegistrationFileEntity> findByRegistrationEntity_Id(Integer id);
}

package com.example.demo.accident;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RegistrationFileRepository extends JpaRepository<RegistrationFileEntity,Integer> {
    List<RegistrationFileEntity> findByRegistrationEntity_Id(Integer id);
}

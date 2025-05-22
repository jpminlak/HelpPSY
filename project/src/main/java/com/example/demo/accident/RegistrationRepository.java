package com.example.demo.accident;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity,Integer> {
    List<RegistrationEntity> findAll(); // 전체출력
}

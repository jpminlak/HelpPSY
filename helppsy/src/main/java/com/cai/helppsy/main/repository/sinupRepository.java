package com.cai.helppsy.main.repository;


import com.cai.helppsy.main.entity.SinupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface sinupRepository extends JpaRepository<SinupEntity,String> {

    SinupEntity findByuserId(String userId);

}

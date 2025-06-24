package com.cai.helppsy.map;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkerRepository extends JpaRepository<Mapmarker, Integer> {
    List<Mapmarker> findAll();
    //Optional<Mapmarker> findById(Integer num);
}

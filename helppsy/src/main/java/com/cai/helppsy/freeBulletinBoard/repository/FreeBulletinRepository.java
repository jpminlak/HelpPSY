package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinRepository extends JpaRepository<FreeBulletin, Integer> {
    List<FreeBulletin> findByTitleContaining(String searchWord);
    List<FreeBulletin> findByTitleContainingOrderByLikesAsc(String searchWord, Sort sort);
}

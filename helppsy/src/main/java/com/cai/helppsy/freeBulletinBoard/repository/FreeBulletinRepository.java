package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinRepository extends JpaRepository<FreeBulletin, Integer> {
    List<FreeBulletin> findByTitleContainingOrderByLikesDesc(String searchWord, Sort sort);
    List<FreeBulletin> findByTitleContainingOrderByLikesAsc(String searchWord, Sort sort);
    List<FreeBulletin> findByTitleContainingOrderByViewsDesc(String searchWord, Sort sort);
    List<FreeBulletin> findByTitleContainingOrderByViewsAsc(String searchWord, Sort sort);
    List<FreeBulletin> findByTitleContainingOrderByNoDesc(String searchWord, Sort sort);
    List<FreeBulletin> findByTitleContainingOrderByNoAsc(String searchWord, Sort sort);
    List<FreeBulletin> findByWriter(String writer);
    List<FreeBulletin> findTop3ByOrderByViewsDesc();
    List<FreeBulletin> findTop3ByOrderByLikesDesc();
}

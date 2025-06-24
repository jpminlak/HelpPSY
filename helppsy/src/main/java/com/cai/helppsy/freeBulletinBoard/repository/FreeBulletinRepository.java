package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinRepository extends JpaRepository<FreeBulletin, Integer> {
    List<FreeBulletin> findTop3ByOrderByViewsDesc();
    List<FreeBulletin> findTop3ByOrderByLikesDesc();
    List<FreeBulletin> findByWriter(String writer); // 작성자(alias)로 게시물 목록 가져오기
}

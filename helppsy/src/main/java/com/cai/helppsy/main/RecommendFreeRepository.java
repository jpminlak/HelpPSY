package com.cai.helppsy.main;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendFreeRepository extends JpaRepository<FreeBulletin, Integer> {

    // 자유게시판 24시간 이내 상위 조회수 5개
    @Query(value = "SELECT * FROM free_bulletin WHERE create_date >= DATE_SUB(NOW(), INTERVAL 1 DAY) ORDER BY views DESC LIMIT 5;", nativeQuery = true)
    List<FreeBulletin> findWithinLast24Hours();

    // 자유게시판 24시간 이내 상위 좋아요 수 5개
    @Query(value = "SELECT * FROM free_bulletin WHERE create_date >= DATE_SUB(NOW(), INTERVAL 1 DAY) ORDER BY likes DESC LIMIT 5;", nativeQuery = true)
    List<FreeBulletin> findWithinLast24HoursLikes();

    // 사고게시판 24시간 이내 전체 상위 조회수 5개
    @Query(value = "SELECT * FROM registration_entity WHERE create_date >= DATE_SUB(NOW(), INTERVAL 1 DAY) ORDER BY post_views DESC LIMIT 5;", nativeQuery = true)
    List<RegistrationEntity> findWithinLast24HoursAccViews();

    // 사고게시판 24시간 이내 지역별 상위 조회수 5개
    @Query(value = "SELECT * FROM registration_entity WHERE create_date >= DATE_SUB(NOW(), INTERVAL 1 DAY) AND region LIKE :region% ORDER BY post_views DESC LIMIT 5;", nativeQuery = true)
    List<RegistrationEntity> findByPostViewsContainingManually(@Param("region") String region);

    //@Query(value = "SELECT * FROM registration_entity WHERE create_date >= DATE_SUB(NOW(), INTERVAL 1 DAY) ORDER BY views DESC LIMIT 5;", nativeQuery = true)
    //List<FreeBulletin> findWithinLast24HoursAccLikes();
}

//    @Query("SELECT f FROM free_bulletin f WHERE f.createdAt >= :oneDayAgo")
//    List<YourEntity> findByCreatedAtAfter(@Param("oneDayAgo") LocalDateTime oneDayAgo);

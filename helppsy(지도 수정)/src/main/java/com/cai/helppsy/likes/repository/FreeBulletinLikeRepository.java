package com.cai.helppsy.likes.repository;

import com.cai.helppsy.likes.entity.FreeBulletinLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinLikeRepository extends JpaRepository<FreeBulletinLike, Integer> {
    FreeBulletinLike findByFreeBulletin_noAndUserName(int no, String userName);
}

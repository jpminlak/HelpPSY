package com.cai.helppsy.likes.repository;

import com.cai.helppsy.likes.entity.FreeBulletinCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBulletinCommentLikeRepository extends JpaRepository<FreeBulletinCommentLike, Integer> {
    FreeBulletinCommentLike findByFreeBulletinComment_noAndUserName(int no, String userName);
}

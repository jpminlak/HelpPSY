package com.cai.helppsy.likes.repository;

import com.cai.helppsy.likes.entity.FreeBulletinReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinReplyLikeRepository extends JpaRepository<FreeBulletinReplyLike, Integer> {
    FreeBulletinReplyLike findByFreeBulletinReply_noAndUserName(int no, String userName);
}

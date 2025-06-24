package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinReplyRepository extends JpaRepository<FreeBulletinReply, Integer> {
    List<FreeBulletinReply> findByFreeBulletinComment_no(Integer no);
}

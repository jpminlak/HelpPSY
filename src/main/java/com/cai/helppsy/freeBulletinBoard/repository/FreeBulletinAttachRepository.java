package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinAttach;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinImageAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeBulletinAttachRepository extends JpaRepository<FreeBulletinAttach, Integer> {
    List<FreeBulletinAttach> findByFreeBulletin_no(Integer no);
}

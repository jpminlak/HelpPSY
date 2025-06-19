package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinImageAttach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBulletinImageAttachRepository extends JpaRepository<FreeBulletinImageAttach, Integer> {
    FreeBulletinImageAttach findByFreeBulletin_no(Integer no);
}

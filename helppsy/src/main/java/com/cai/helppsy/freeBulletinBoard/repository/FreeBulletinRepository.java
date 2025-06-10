package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBulletinRepository extends JpaRepository<FreeBulletin, Integer> {
}

package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulletinRepository extends JpaRepository<Bulletin, Integer> {

}

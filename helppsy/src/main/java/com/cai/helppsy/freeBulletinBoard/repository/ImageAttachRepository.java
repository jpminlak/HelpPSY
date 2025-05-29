package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.ImageAttach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageAttachRepository extends JpaRepository<ImageAttach, Integer> {
    ImageAttach findByNo(Integer no);
}

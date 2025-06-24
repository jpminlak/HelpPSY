package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinAttach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinAttachRepository extends JpaRepository<FreeBulletinAttach, Integer> {
    List<FreeBulletinAttach> findByFreeBulletin_no(Integer no);
    void deleteByFreeBulletin_no(Integer no);
    void deleteByFileName(String fileName);
}

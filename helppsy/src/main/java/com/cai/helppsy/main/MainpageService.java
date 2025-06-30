package com.cai.helppsy.main;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.RegistrationRepository;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MainpageService {
    private final FreeBulletinRepository freeBulletinRepository;
    private final RegistrationRepository registrationRepository;
    private final RecommendFreeRepository recommendFreeRepository;

    public List<FreeBulletin> top3ViewFree() {
        return freeBulletinRepository.findTop3ByOrderByViewsDesc();
    }

    public List<FreeBulletin> top3ViewLikes() {
        return freeBulletinRepository.findTop3ByOrderByLikesDesc();
    }

    public List<RegistrationEntity> top3ViewAccident() {
        return registrationRepository.findTop3ByOrderByPostViewsDesc();
    }

    public List<FreeBulletin> free24hours() {
        //LocalDateTime now = LocalDateTime.now();
        //LocalDateTime minus24Hours = now.minusHours(24);
        return recommendFreeRepository.findWithinLast24Hours();
    }

    public List<FreeBulletin> free24hoursLikes() {
        return recommendFreeRepository.findWithinLast24HoursLikes();
    }

    public List<RegistrationEntity> acc24hours() {
        return recommendFreeRepository.findWithinLast24HoursAccViews();
    }

    public List<RegistrationEntity> acc24hoursRegion(String region) {
        return recommendFreeRepository.findByPostViewsContainingManually(region);
    }

}

//    public void recommendFreeBulletin() {
//        List<FreeBulletin> recentEntities = recommendFreeRepository.findWithinLast24Hours();
//        for (FreeBulletin freeBulletin : recentEntities) {
//            System.out.println(freeBulletin.getContent());
//        }
//    }

//      LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
//      List<FreeBulletin> recentEntities = recommendFreeRepository.findByCreatedAtAfter(oneDayAgo);
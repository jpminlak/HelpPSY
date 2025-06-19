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

    public List<FreeBulletin> top3ViewFree() {
        return freeBulletinRepository.findTop3ByOrderByViewsDesc();
    }
    public List<FreeBulletin> top3ViewLikes() {
        return freeBulletinRepository.findTop3ByOrderByLikesDesc();
    }

    public List<RegistrationEntity> top3ViewAccident(){
        return registrationRepository.findTop3ByOrderByPostViewsDesc();
    }
}

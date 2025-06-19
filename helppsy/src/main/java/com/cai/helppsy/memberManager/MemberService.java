package com.cai.helppsy.memberManager;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.RegistrationRepository;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final FreeBulletinRepository freeBulletinRepository;
    private final RegistrationRepository registrationRepository;

    public List<RegistrationEntity> getPostsByAlias(String alias) {     // 사고게시판 회원별 글 작성 목록
        return registrationRepository.findByAlias(alias);
    }
}

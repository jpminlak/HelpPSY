package com.cai.helppsy.main.service;


import com.cai.helppsy.main.entity.SinupEntity;
import com.cai.helppsy.main.repository.sinupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class sinupService {

    private final sinupRepository sinuprepository;


    public void sinup(SinupEntity sinupentity) { // 회원가입
        sinuprepository.save(sinupentity);
    }

    public SinupEntity login(String userId) {
        return sinuprepository.findByuserId(userId);
    }

    public boolean existsById(String userId) {
        return sinuprepository.existsByUserId(userId); // 직접 선언한 메서드 호출
    }
}

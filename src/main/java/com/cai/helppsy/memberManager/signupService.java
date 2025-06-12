package com.cai.helppsy.memberManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class signupService {
    private final signupRepository signupRepository;
    public void signup(SignupEntity sinupentity) { // 회원가입
        signupRepository.save(sinupentity);
    }   // 회원 가입
    public SignupEntity login(String userId) {
        return signupRepository.findByuserId(userId);
    }   // 회원 별칭 가져오기 (세션유지용)
    public boolean existsById(String userId) {
        return signupRepository.existsByUserId(userId); // 직접 선언한 메서드 호출
    }
}
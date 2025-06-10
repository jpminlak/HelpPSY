package com.cai.helppsy.memberManager;


import org.springframework.data.jpa.repository.JpaRepository;

public interface signupRepository extends JpaRepository<SignupEntity,String> {
    SignupEntity findByuserId(String userId);
    boolean existsByUserId(String userId);
    SignupEntity findByAlias(String alias);
}

package com.cai.helppsy.memberManager;


import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class SignupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 역할 숫자 1씩 증감
    private Integer id; // 저장 번호

    @Column(length = 20)
    private String userId; // 아이디

    @Column(length = 20)
    private String userPass; // 비밀번호

    @Column(length = 15)
    private String alias; // 별명

    @Column(length = 9)
    private String division; // 일반인, 설계사 구분

    @Column(length = 14)
    private String dNum;    //설계사 고유번호

    @Column(length = 1000)
    private String intro;   //소개글

    @Column(length = 1000)
    private String note;    //쪽지

    @OneToMany(mappedBy = "signupEntity",cascade = CascadeType.REMOVE)
    // mappedBy=참조엔티티속성명 , cascade = CascadeType.REMOVE = 질문을 삭제하면 답변도 같이 삭제되게끔 하는 코드
    private List<RegistrationEntity> registrationentity;
}

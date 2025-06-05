package com.cai.helppsy.accidentBulleinBoard.serviece;


import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentrepository;

    // 댓글 저장
    public void SaveComment(CommentEntity commententity, Integer FKId) {
        // 등록글의 ID로 RegistrationEntity 객체를 생성 (영속성 X, ID만 연결)
        RegistrationEntity registration = new RegistrationEntity();
        // 글테이블(RegistrationEntity) 객체에 작성된 글로 새로운 객체 생성
        registration.setId(FKId);
        // CommentEntity에 등록글 연결
        // CommentEntity댓글 테이블에 위에 새로 생성한 글테이블(RegistrationEntity) 객체를 넣어 id값 맵핑
        commententity.setRegistrationEntity(registration);
        commentrepository.save(commententity); // 이후 저장
    }

    // 외래키 id로 댓글가져오기
    public List<CommentEntity> getComment(Integer id){
        return commentrepository.findByRegistrationEntity_Id(id);
    }

//    // 외래키 id로 댓글가져오기
//    public List<CommentEntity> getComment(Integer id){
//        return commentrepository.findAll();
//    }

    // id로 사용자 댓글 작성자 가져오기


    // 댓글 삭제하기
    public void deleteComment(Integer id){
        commentrepository.deleteById(id);
    }


}

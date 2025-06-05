package com.cai.helppsy.accidentBulleinBoard.controller;


import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.serviece.CommentService;
import com.cai.helppsy.accidentBulleinBoard.serviece.RegistrationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommentContorller {

    private final CommentService commentservice;


    // 댓글 저장 (로그인 public String login에서 세션유지하는 값을 클라이언트에서 별칭 뽑아옴)
    @PostMapping("/comment")
        // commententity에는 댓글내용/작성자 별칭(세션값뽑아옴)/글작성번호(글테이블id) 넘겨받음
    public String comment(@ModelAttribute CommentEntity commententity
    , @RequestParam("registrationId") Integer registrationId){
        commentservice.SaveComment(commententity,registrationId); //댓글테이블에 저장
        // 순서1) 외래키 id로 테이블조회후
        return "redirect:/accidentview/"+ registrationId;
    }

    // 해당 댓글 삭제하기
    @PostMapping("/commentdelete")
    public String commentdelete(@RequestParam("commentId") Integer id
            , @RequestParam("userAlias") String userAlias
            ,@RequestParam("registrationId") Integer registration_entity_id
            ,HttpSession session) {
        System.out.println("삭제하기 들어왔니?????");

        System.out.println("------------------------댓글삭제는 여기서 확인하세용");
        System.out.println(id+"=댓글작성번호");
        System.out.println(userAlias);
        System.out.println(registration_entity_id);
        System.out.println("------------------------if문 확인하기");
        if(session.getAttribute("userAlias").equals(userAlias)){
            commentservice.deleteComment(id);
        }
        return "redirect:/accidentview/" + registration_entity_id;
    }



}

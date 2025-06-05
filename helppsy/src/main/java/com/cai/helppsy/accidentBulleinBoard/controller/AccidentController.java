package com.cai.helppsy.accidentBulleinBoard.controller;

import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationFileEntity;
import com.cai.helppsy.accidentBulleinBoard.serviece.CommentService;
import com.cai.helppsy.accidentBulleinBoard.serviece.RegistrationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class AccidentController {

    private final RegistrationService registrationService;
    private final CommentService commentservice;

    // 사고 게시판 메인화면
    @GetMapping("accidentmain")
    public String main() {
        return "redirect:/return";
    }

    // 사고 게시판 글쓰기 화면
    @GetMapping("accidentwriting")
    public String accidentwriting() {
        return "accident/accidentwriting";
    }

    // 사고 게시판 작성글 db업로드
    @PostMapping("/registration")
    public String writing(@ModelAttribute RegistrationEntity registrationEntity
            ,@RequestParam("file") MultipartFile[] file) throws Exception {
//        registrationEntity.setAlias(session.getAttribute("userAlias").toString());
        // session.getAttribute("userAlias").toString() 세션에 저장되어있는 별명을 바로 엔티티에 저장
        registrationService.write(registrationEntity); // 작성글 서비스단 보내기
        registrationService.files(file, registrationEntity); // 사진 서비스단 보내기
        return "redirect:/return";
    }

    // db업로드 리스트 전체출력
    @GetMapping("/return")
    public String list(Model model) {
        List<RegistrationEntity> writegetlist = registrationService.writegetlist();
        model.addAttribute("writegetlist", writegetlist);
        return "accident/accidentmain";
    }

    // 제목 상세보기(글정보)
    @GetMapping("/accidentview/{id}")
    public String accidentview(Model model, @PathVariable("id") Integer id) {
        // @ 글작성 정보 가져오기
        //@PathVariable=RUL 경로의 값을 메서드 파라미터로 바인딩
        System.out.println("-----------------------------글정보 보기에요");
        System.out.println(id);
        System.out.println("-----------------------------");
        Optional<RegistrationEntity> viewOptional = registrationService.getaccidentview(id);
        // RegistrationEntity의 id값을 Optional로 받아옴
        // Optional = findById()는 항상 Optional<엔티티>를 반환하는 것이 권장
        RegistrationEntity view = viewOptional.get();
        // 받아온 viewOptional에서 id를 가져와 RegistrationEntity타입으로 view에 저장
        model.addAttribute("view", view);
        // @ 파일이름 가져오기
        List<RegistrationFileEntity> filename = registrationService.getfilename(id);
        model.addAttribute("filename", filename);

        // 댓글보기
        // 순서1) 외래키 id로 테이블조회후
        List<CommentEntity> commentList = commentservice.getComment(id);

        // 순서2) CommentEntity타입으로 가져온 목록을 commentlist에 대입
        model.addAttribute("commenet",commentList);

        return "accident/accidentview";
    }

    // 글 삭제하기
    @PostMapping("/deleteAccident")
    public String deleteAccident(@RequestParam("id") Integer id, HttpSession session){
        Optional<RegistrationEntity> viewOptional = registrationService.getaccidentview(id);
        RegistrationEntity view = viewOptional.get();
        // 로그인 회원 별명과 글작성회원 별명이 같을때 삭제 가능
        if(session.getAttribute("userAlias").equals(view.getAlias())){
        registrationService.deleteAccident(id);
        }
        return "redirect:/return";
    }


}

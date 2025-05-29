package com.cai.helppsy.accidentBulleinBoard.controller;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationFileEntity;
import com.cai.helppsy.accidentBulleinBoard.serviece.RegistrationService;
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
            , MultipartFile[] file) throws Exception {
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
        Optional<RegistrationEntity> viewOptional = registrationService.getaccidentview(id);
        // RegistrationEntity의 id값을 Optional로 받아옴
        // Optional = findById()는 항상 Optional<엔티티>를 반환하는 것이 권장
        RegistrationEntity view = viewOptional.get();
        // 받아온 viewOptional에서 id를 가져와 RegistrationEntity타입으로 view에 저장
        model.addAttribute("view", view);

        // @ 파일이름 가져오기
        List<RegistrationFileEntity> filename = registrationService.getfilename(id);
        model.addAttribute("filename", filename);
        return "accident/accidentview";
    }

}

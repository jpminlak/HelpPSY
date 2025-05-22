package com.example.demo.accident;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class accidentController {

    private final RegistrationService registrationService;

    @GetMapping("accidentmain") // 사고 게시판 메인화면
    public String main(){
        return "accident/accidentmain";
    }

    @GetMapping("accidentwriting") // 사고 게시판 글쓰기 화면
    public String accidentwriting(){
        return "accident/accidentwriting";
    }

    @PostMapping("/registration") // 사고 게시판 작성글 db업로드
    public String writing(@ModelAttribute RegistrationEntity registration
    ,@ModelAttribute RegistrationFileEntity registrationfileentity
    ,MultipartFile[] file) throws Exception{
        registrationService.write(registration); // 작성글 서비스단 보내기
        registrationService.files(registrationfileentity,file); // 사진 서비스단 보내기
        return "redirect:/return";
    }

    @GetMapping("/return")
    public String list(Model model){
        List<RegistrationEntity> writegetlist = registrationService.writegetlist();
        model.addAttribute("writegetlist",writegetlist);
        return "accident/accidentmain";
    }
}

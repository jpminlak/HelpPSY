package com.example.demo.accident;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class accidentController {

    private final IF_RegistrationService registrationService;

    @GetMapping("accidentmain") // 사고 게시판 메인화면
    public String main(){
        return "accident/accidentmain";
    }

    @GetMapping("accidentwriting") // 사고 게시판 글쓰기 화면
    public String accidentwriting(){
        return "accident/accidentwriting";
    }

    @PostMapping("/registration") // 사고 게시판
    public String writing(Model model,
                          @ModelAttribute RegistrationEntity registration) {
        registrationService.write(registration); // 사진배열 서비스단 보내기
//        System.out.println(registration.getTitle()); // 제목
//        System.out.println(registration.getAccident()); // 사고
//        System.out.println(registration.getRegion()); // 지역
//        System.out.println(registration.getRating()); // 차등급
//        System.out.println(registration.getType()); // 차종류
//        System.out.println(file[0].getOriginalFilename()); // 파일사진이름
//        System.out.println(file[1].getOriginalFilename()); // 파일사진이름
//      model.addAttribute("message","글작성이 완료되었습니다");
//      model.addAttribute("searchUrl","/registration/list");
        return "redirect:/file";
    }
    @PostMapping("/file") // 사고 게시판
    public String file(Model model,
                          @ModelAttribute RegistrationFileEntity registrationfileentity
            , MultipartFile[] file) throws Exception {
        registrationService.write(registrationfileentity, file); // 사진배열 서비스단 보내기
//        System.out.println(registration.getTitle()); // 제목
//        System.out.println(registration.getAccident()); // 사고
//        System.out.println(registration.getRegion()); // 지역
//        System.out.println(registration.getRating()); // 차등급
//        System.out.println(registration.getType()); // 차종류
//        System.out.println(file[0].getOriginalFilename()); // 파일사진이름
//        System.out.println(file[1].getOriginalFilename()); // 파일사진이름
//      model.addAttribute("message","글작성이 완료되었습니다");
//      model.addAttribute("searchUrl","/registration/list");
        return "accident/accidentwriting";
    }


}

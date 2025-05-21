package com.example.demo.accident;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class accidentController {

    private final IF_RegistrationService registrationService;

    @GetMapping("accidentmain")
    public String main(){
        return "accident/accidentmain";
    }

    @GetMapping("accidentwriting")
    public String accidentwriting(){
        return "accident/accidentwriting";
    }

    @PostMapping("/registration")
    public String writing(Model model,
                          @ModelAttribute RegistrationEntity registration
    , MultipartFile file) throws Exception{

        registrationService.write(registration,file);

      model.addAttribute("message","글작성이 완료되었습니다");
      model.addAttribute("searchUrl","/registration/list");

        return "accident/accidentwriting";
    }

}

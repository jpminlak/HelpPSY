package com.example.demo.save;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Member;
@RequiredArgsConstructor
@Controller
public class SaveController {

    private final IF_SaveService saveservice;

    @GetMapping("/testmain") // 메인화면
    public String main(){
        return "testmain";
    }

    @GetMapping("/user") // 회원가입 메인화면
    public String user(){
        return "user";
    }

    @RequestMapping(value = "/accession",method = RequestMethod.POST) // 가입기능
    public String accession(Model model, @ModelAttribute Save save){
        saveservice.save(save);

        return "testmain";
    }
}

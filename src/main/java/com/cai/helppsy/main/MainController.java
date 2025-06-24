package com.cai.helppsy.main;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping("main")
    public String main(HttpSession session) {
//        System.out.println("___________________________________");
//        System.out.println(session.getAttribute("userId"));
//        System.out.println(session.getAttribute("userPass"));
//        System.out.println(session.getAttribute("userAlias"));
//        System.out.println("___________________________________");
        System.out.println("메인 페이지");
        return "main/mainPage";
    }
}
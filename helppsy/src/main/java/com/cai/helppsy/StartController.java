package com.cai.helppsy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping("/")
    public String start(){
        System.out.println("메인 페이지 시작");
        return "mainPage";
    }

    @GetMapping("/free")
    public String free(){
        System.out.println("자유 게시판");
        return "free";
    }
    @GetMapping("/accident")
    public String accident(){
        System.out.println("사고 게시판");
        return "accident";
    }
    @GetMapping("/inquiry")
    public String inquiry(){
        System.out.println("문의하기");
        return "inquiry";
    }
    @GetMapping("/map")
    public String map(){
        System.out.println("지도 페이지 입장");
        return "/map/map";
    }
}

package com.cai.helppsy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping("/")
    public String start(){
        System.out.println("메인 페이지 시작");
        return "start";
    }

    @GetMapping("/map")
    public String map(){
        System.out.println("지도 페이지 입장");
        return "/map/map";
    }
    @GetMapping("/2dmap")
    public String map2d(){
        System.out.println("2D 지도 페이지 입장");
        return "/map/2dmap";
    }
}

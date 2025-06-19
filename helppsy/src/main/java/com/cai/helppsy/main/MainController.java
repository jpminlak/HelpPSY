package com.cai.helppsy.main;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainpageService mainpageService;

    @GetMapping("main")
    public String main(HttpSession session, Model model) {
        System.out.println("메인 페이지");
        List<FreeBulletin> top3ViewFree = mainpageService.top3ViewFree();
        List<FreeBulletin> top3ViewLikes = mainpageService.top3ViewLikes();
        List<RegistrationEntity> top3ViewAccident = mainpageService.top3ViewAccident();
        model.addAttribute("top3ViewFree", top3ViewFree);
        model.addAttribute("top3ViewLikes", top3ViewLikes);
        model.addAttribute("top3ViewAccident", top3ViewAccident);
        return "main/mainPage";
    }
}

//    @GetMapping("/")
//    public String map_cocoa(){
//        return "map/map_cocoa";
//    }
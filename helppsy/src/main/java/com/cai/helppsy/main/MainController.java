package com.cai.helppsy.main;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainpageService mainpageService;

    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        System.out.println("메인 페이지");
        List<FreeBulletin> free24hours = mainpageService.free24hours();
        List<FreeBulletin> free24hoursLikes = mainpageService.free24hoursLikes();
        List<FreeBulletin> top3ViewFree = mainpageService.free24hours();
        List<FreeBulletin> top3ViewLikes = mainpageService.free24hoursLikes();
        List<RegistrationEntity> top3ViewAccident = mainpageService.acc24hours();
        model.addAttribute("top3ViewFree", top3ViewFree);
        model.addAttribute("top3ViewLikes", top3ViewLikes);
        model.addAttribute("top3ViewAccident", top3ViewAccident);
        return "/main/mainPage";
    }

    @GetMapping("/main/local/{region}")
    public String region(Model model, @PathVariable("region") String region){
    //public String region(Model model){
        List<FreeBulletin> free24hours = mainpageService.free24hours();
        List<FreeBulletin> free24hoursLikes = mainpageService.free24hoursLikes();
        List<FreeBulletin> top3ViewFree = mainpageService.free24hours();
        List<FreeBulletin> top3ViewLikes = mainpageService.free24hoursLikes();
        //List<RegistrationEntity> top3ViewAccident = mainpageService.acc24hours();
        model.addAttribute("top3ViewFree", top3ViewFree);
        model.addAttribute("top3ViewLikes", top3ViewLikes);
        //model.addAttribute("top3ViewAccident", top3ViewAccident);

        // 지역 게시물 추가
        List<RegistrationEntity> top3ViewAccidentRegion = mainpageService.acc24hoursRegion(region);
        model.addAttribute("top3ViewAccidentRegion", top3ViewAccidentRegion);
        return "main/mainPage";
    }
}

//@GetMapping("/")
//public String map_cocoa(){
//    return "map/map_cocoa2";
//}
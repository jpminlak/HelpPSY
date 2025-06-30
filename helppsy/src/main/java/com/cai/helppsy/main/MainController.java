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
//        List<FreeBulletin> free24hours = mainpageService.free24hours();
//        List<FreeBulletin> free24hoursLikes = mainpageService.free24hoursLikes();
        List<FreeBulletin> top5ViewFree = mainpageService.free24hours();
        List<FreeBulletin> top5ViewLikes = mainpageService.free24hoursLikes();
        List<RegistrationEntity> top5ViewAccident = mainpageService.acc24hours();
        model.addAttribute("top5ViewFree", top5ViewFree);
        model.addAttribute("top5ViewLikes", top5ViewLikes);
        model.addAttribute("top5ViewAccident", top5ViewAccident);
        return "/main/mainPage";
        //return "/main/main_exp";
    }

    @GetMapping("/main/local/{region}")
    public String region(Model model, @PathVariable("region") String region){
        List<FreeBulletin> top5ViewFree = mainpageService.free24hours();
        List<FreeBulletin> top5ViewLikes = mainpageService.free24hoursLikes();
        List<RegistrationEntity> top5ViewAccident = mainpageService.acc24hours();
        model.addAttribute("top5ViewFree", top5ViewFree);
        model.addAttribute("top5ViewLikes", top5ViewLikes);
        model.addAttribute("top5ViewAccident", top5ViewAccident);

        // 지역 게시물 추가
        List<RegistrationEntity> top5ViewAccidentRegion = mainpageService.acc24hoursRegion(region);
        model.addAttribute("top5ViewAccidentRegion", top5ViewAccidentRegion);
        // 지역별 게시물 선택하여 url이 바뀔 때도 처음 화면에 추천글이 보이게 하기 위해 같이 보냄.
        model.addAttribute("mode", "region");
        return "main/mainPage";
        //return "/main/main_exp";
    }
}

//@GetMapping("/")
//public String map_cocoa(){
//    return "map/map_cocoa2";
//}
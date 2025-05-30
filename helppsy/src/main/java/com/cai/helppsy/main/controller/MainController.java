package com.cai.helppsy.main.controller;

import com.cai.helppsy.main.entity.SinupEntity;
import com.cai.helppsy.main.service.sinupService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping("main")
    public String main(HttpSession session){
        System.out.println("___________________________________");
        System.out.println(session.getAttribute("userId"));
        System.out.println(session.getAttribute("userPass"));
        System.out.println(session.getAttribute("userAlias"));
        System.out.println("___________________________________");
        return "main/mainPage";
    }

    private final sinupService sinupservice;


    @GetMapping("signUpMain")
    public String sinupMain(){
        return "memberManager/signUp";
    }

    // 회원가입 하기
    @PostMapping(value = "/signUp")
    public String sinup(@ModelAttribute SinupEntity sinupentity){
        sinupservice.sinup(sinupentity);
        return "main/mainPage";
    }
    @GetMapping("signInMain")
    public String logInMain(){
        return "memberManager/signIn";
    }

    // 로그인 (세션유지)
    @PostMapping("/login")
    public String login(@RequestParam("userId") String userId
            , @RequestParam("userPass") String userPass
            , HttpSession session, Model model) {
        SinupEntity user = sinupservice.login(userId);
        if (user.getUserId().equals(userId) && user.getUserPass().equals(userPass)) { // 입력한 id,pass가 db와 같을때
            System.out.println("-------------------");
            System.out.println(user);
            System.out.println(user.getUserId());
            System.out.println(user.getUserPass());
            System.out.println(user.getAlias());
            System.out.println(user.getIntro());
            System.out.println("-------------------");

            session.setAttribute("userId", user.getUserId()); // 세션에 id저장
            session.setAttribute("userAlias", user.getAlias()); // 세션에 별명저장
            session.setAttribute("userPass", user.getUserPass()); // 세션에 비밀번호 저장
            session.setAttribute("Intro",user.getIntro());  //세션에 소개글 저장
        }
        model.addAttribute("user", session);
        return "redirect:/main";
    }



    // 로그아웃 (세션삭제)
    @GetMapping("/logout")
    public String logout(HttpSession session){
        System.out.println(session.getAttribute("userId"));
        System.out.println(session.getAttribute("userPass"));
        session.removeAttribute("userId"); // 세션 userid값 삭제
        session.removeAttribute("userPass"); // 세션 pass삭제
        session.removeAttribute("userAlias");
        session.removeAttribute("Intro");
        System.out.println("들어왔어!!!!!!!!!!!!!!!!!!!!");
        System.out.println(session.getAttribute("userId"));
        System.out.println(session.getAttribute("userPass"));
        return "redirect:/main";
    }


    @GetMapping("/profile")
    public String profile(){
        return "memberManager/profile";
    }

    @GetMapping("/profile_Update")
    public String profile_Update(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/main";

        SinupEntity user = sinupservice.login(userId);
        model.addAttribute("user", user);
        return "memberManager/profile_Update";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute SinupEntity updatedUser, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/main";

        SinupEntity user = sinupservice.login(userId);
        if (user != null) {
            user.setAlias(updatedUser.getAlias());
            user.setUserPass(updatedUser.getUserPass());
            user.setIntro(updatedUser.getIntro());
            sinupservice.sinup(user);  // save()로 덮어쓰기
            session.setAttribute("userAlias", user.getAlias());  // 세션도 업데이트
            session.setAttribute("Intro", user.getIntro());
        }

        return "redirect:/profile";
    }

    @GetMapping("/note")
    public String note(){
        return "memberManager/note";
    }


    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam String userId) {
        boolean exists = sinupservice.existsById(userId); // 여기 사용 가능
        return exists ? "DUPLICATE" : "OK";
    }


}

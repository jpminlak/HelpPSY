package com.cai.helppsy.memberManager;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Controller
public class memberController {
    private final signupRepository signupRepository;
    private final signupService signupservice;

    @GetMapping("signUpMain")
    public String sinupMain() {
        return "memberManager/signUp";
    }

    // 회원가입 하기
    @PostMapping(value = "/signUp")
    public String sinup(@ModelAttribute SignupEntity signupEntity) {
        signupservice.signup(signupEntity);
        return "main/mainPage";
    }

    @GetMapping("signInMain")
    public String logInMain() {
        return "memberManager/signIn";
    }

    // 로그인 (세션유지)
    @PostMapping("/login")
    public String login(@RequestParam("userId") String userId
            , @RequestParam("userPass") String userPass
            , HttpSession session, Model model) {
        SignupEntity user = signupservice.login(userId);
        if (user != null) {
            if (user.getUserId().equals(userId) && user.getUserPass().equals(userPass)) { // 입력한 id,pass가 db와 같을때
//            System.out.println("-------------------");
//            System.out.println(user);
//            System.out.println(user.getUserId());
//            System.out.println(user.getUserPass());
//            System.out.println(user.getAlias());
//            System.out.println(user.getIntro());
//            System.out.println("-------------------");

                session.setAttribute("userId", user.getUserId()); // 세션에 id저장
                session.setAttribute("userAlias", user.getAlias()); // 세션에 별명저장
                session.setAttribute("division", user.getDivision());   // 세션에 설계사,일반인(구분) 저장
                session.setAttribute("dNum", user.getDNum());   // 세션에 설계사 번호 저장
                session.setAttribute("userPass", user.getUserPass()); // 세션에 비밀번호 저장
                session.setAttribute("Intro", user.getIntro());  //세션에 소개글 저장
            }
            model.addAttribute("user", session);
        }
        return "redirect:/main";
    }

    // 로그아웃 (세션삭제)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println(session.getAttribute("userId"));
        System.out.println(session.getAttribute("userPass"));
        session.removeAttribute("userId"); // 세션 userid값 삭제
        session.removeAttribute("userPass"); // 세션 pass삭제
        session.removeAttribute("userAlias");
        session.removeAttribute("division");
        session.removeAttribute("dNum");
        session.removeAttribute("Intro");
//        System.out.println("들어왔어!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(session.getAttribute("userId"));
//        System.out.println(session.getAttribute("userPass"));
        return "redirect:/main";
    }


    @GetMapping("/profile")
//    public String profile(){
    public String profile(@RequestParam(value = "alias", required = false) String alias, Model model) {
        SignupEntity signupEntity = signupRepository.findByAlias(alias);
//        if(sinupEntity != null) {
//            System.out.println(sinupEntity.getId());
//            System.out.println(sinupEntity.getUserId());
//            System.out.println(sinupEntity.getAlias());
//        }
//        System.out.println(sinupEntity.getDivision());
        System.out.println("-----------1------------ -");
        System.out.println(signupEntity);
        System.out.println(alias);
        System.out.println("------------2----------- -");
        model.addAttribute("member", signupEntity);
        return "memberManager/profile";
    }

    @GetMapping("/profile_Update")
    public String profile_Update(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/main";

        SignupEntity user = signupservice.login(userId);
        model.addAttribute("user", user);
        return "memberManager/profile_Update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute SignupEntity updatedUser, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null)
            return "redirect:/main";

        SignupEntity user = signupservice.login(userId);
        if (user != null) {
            user.setAlias(updatedUser.getAlias());
            user.setUserPass(updatedUser.getUserPass());
            user.setDivision(updatedUser.getDivision());
            user.setIntro(updatedUser.getIntro());
            user.setDNum(updatedUser.getDNum());
            signupservice.signup(user);  // save()로 덮어쓰기
            session.setAttribute("userAlias", user.getAlias());  // 세션도 업데이트
            session.setAttribute("Intro", user.getIntro());
            System.out.println(user.getAlias());
        }

        String redirectUrl = UriComponentsBuilder
                .fromPath("/profile")
                .queryParam("alias", user.getAlias())
                .build()
                .encode()   //한국어 자동 인코딩 (Tomcat 예외 / 한글 깨짐)
                .toUriString();

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam String userId) {
        boolean exists = signupservice.existsById(userId); // 여기 사용 가능
        return exists ? "DUPLICATE" : "OK";
    }
}
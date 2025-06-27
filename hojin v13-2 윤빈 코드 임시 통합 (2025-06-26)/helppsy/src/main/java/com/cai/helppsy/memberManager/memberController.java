package com.cai.helppsy.memberManager;

import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.service.CommentReplyService;
import com.cai.helppsy.accidentBulleinBoard.service.CommentService;
import com.cai.helppsy.accidentBulleinBoard.service.RegistrationService;
import com.cai.helppsy.freeBulletinBoard.dto.FreeBulletinDTO;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class memberController {
    private final RegistrationService registrationService; // 제보게시판
    private final CommentService commentService; // 제보게시판
    private final CommentReplyService commentReplyService; // 제보게시판
    private final signupRepository signupRepository;
    private final signupService signupservice;
    private final FreeBulletinRepository freeBulletinRepository;



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
//                session.setAttribute("division", user.getDivision());   // 세션에 설계사,일반인(구분) 저장
//                session.setAttribute("dNum", user.getDNum());   // 세션에 설계사 번호 저장
                session.setAttribute("userPass", user.getUserPass()); // 세션에 비밀번호 저장
                session.setAttribute("Intro", user.getIntro());  //세션에 소개글 저장
                model.addAttribute("user", session);
                return "redirect:/main";
            }
        }
        model.addAttribute("status", "loginFail");
        return "memberManager/signIn";
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
        System.out.println("-----------1------------ -");
        System.out.println(signupEntity);
        System.out.println(alias);
        System.out.println("------------2----------- -");
        model.addAttribute("member", signupEntity);
        // 제보게시판 글 리스트 추가
        List<RegistrationEntity> accidentPosts = registrationService.getPostsByAlias(alias);
        model.addAttribute("accidentPosts", accidentPosts);
        // 자유게시판 글 리스트 추가
        List<FreeBulletin> freeBulletins = freeBulletinRepository.findByUserId(signupRepository.findByAlias(alias).getUserId());
        List<FreeBulletinDTO> freeBulletinDTOList = new ArrayList<>();

        if(freeBulletins != null){
            for(FreeBulletin fb : freeBulletins){
                FreeBulletinDTO freeBulletinDTO = new FreeBulletinDTO();
                freeBulletinDTO.setNo(fb.getNo());
                freeBulletinDTO.setTitle(fb.getTitle());
                freeBulletinDTO.setContent(fb.getContent());
                freeBulletinDTO.setThumbnail(fb.getThumbnail());
                freeBulletinDTO.setWriter(alias);
                freeBulletinDTO.setUserId(fb.getUserId());
                freeBulletinDTO.setViews(fb.getViews());
                freeBulletinDTO.setLikes(fb.getLikes());
                freeBulletinDTO.setCreateDate(fb.getCreateDate());
                freeBulletinDTO.setProfileImgName(signupRepository.findByAlias(alias).getProfileImage());
                freeBulletinDTOList.add(freeBulletinDTO);
            }
        }
        model.addAttribute("freePosts", freeBulletinDTOList);
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
    public String update(@ModelAttribute SignupEntity updatedUser,
                         @RequestParam(value = "Ffile", required = false) MultipartFile profileImage,
                         HttpSession session)  {

        registrationService.setSignupAlias(updatedUser.getAlias(),updatedUser.getId()); // 민우로직추가
        commentService.setCommentSignupAlias(updatedUser.getAlias(),updatedUser.getId()); // 민우로직추가
        commentReplyService.setReplyAlias(updatedUser.getAlias(),updatedUser.getId()); // 민우로직추가

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(profileImage);
        System.out.println(profileImage.getSize());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/main";
        }
        SignupEntity user = signupservice.login(userId);

        if (user != null) {
            // 일반 정보 업데이트
            user.setAlias(updatedUser.getAlias());
            user.setUserPass(updatedUser.getUserPass());
            user.setIntro(updatedUser.getIntro());


            if (!profileImage.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String uploadDir = System.getProperty("user.dir")+"/files/profile" ;
                //System.out.println("파일 저장 경로: " + uploadDir);

                String filename = uuid + "_" + profileImage.getOriginalFilename();
                File dir = new File(uploadDir, filename);

                try {
                    profileImage.transferTo(dir);
                    System.out.println("----------------------------------------------------1212121");
                    user.setProfileImage(filename); // DB에 저장
                    System.out.println("----------------------------------------------------1212121");
                } catch (IOException e) {
                    e.printStackTrace();
                    // 저장 실패 시 처리 로직 추가 가능
                }
            }

            signupservice.signup(user); // 저장
            session.setAttribute("userAlias", user.getAlias());
            session.setAttribute("Intro", user.getIntro());
        }

        String redirectUrl = UriComponentsBuilder
                .fromPath("/profile")
                .queryParam("alias", user.getAlias())
                .build()
                .encode()
                .toUriString();

        return "redirect:" + redirectUrl ;
    }

    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam String userId) {
        boolean exists = signupservice.existsById(userId); // 여기 사용 가능
        return exists ? "DUPLICATE" : "OK";
    }

}
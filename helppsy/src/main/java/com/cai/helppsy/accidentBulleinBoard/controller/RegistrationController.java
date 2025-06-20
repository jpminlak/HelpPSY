package com.cai.helppsy.accidentBulleinBoard.controller;

import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationFileEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.RegistrationRepository;
import com.cai.helppsy.accidentBulleinBoard.service.CommentService;
import com.cai.helppsy.accidentBulleinBoard.service.RegistrationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class RegistrationController {

    private final RegistrationService registrationService;
    private final CommentService commentservice;
    private final RegistrationRepository registrationRepository;


    // 사고 게시판 메인화면
    @GetMapping("accidentmain")
    public String main() {
        return "redirect:/return";
    }

    // 사고 게시판 글쓰기 화면
    @GetMapping("accidentwriting")
    public String accidentwriting() {
        return "accident/accidentwriting";
    }

    // 사고 게시판 작성글 db업로드
    @PostMapping("/registration")
    public String writing(@ModelAttribute RegistrationEntity registrationEntity){
        System.out.println("--------------------사고 게시판 작성글 ");
        System.out.println("위도 :" + registrationEntity.getLatitude());
        System.out.println("경도 :" + registrationEntity.getLongitude());
        System.out.println("--------------------사고 게시판 작성글 ");
        // ,@RequestParam("file") MultipartFile[] file) throws Exception {
//        registrationEntity.setAlias(session.getAttribute("userAlias").toString());
        // session.getAttribute("userAlias").toString() 세션에 저장되어있는 별명을 바로 엔티티에 저장
        registrationService.write(registrationEntity); // 작성글 서비스단 보내기
//        registrationService.files(file, registrationEntity); // 사진 서비스단 보내기
        return "redirect:/return";
    }

    // 게시글 전체출력
    @GetMapping("/getlist")
    public String getlist(Model model) {
        // 전체리스트 출력
        List<RegistrationEntity> writegetlist = registrationService.writegetlist();
        model.addAttribute("writegetlist", writegetlist);


        return "accident/accidentmain";
    }

    // 제목 상세보기(글정보)
    @GetMapping("/accidentview/{id}")
    public String accidentview(Model model, @PathVariable("id") Integer id) {
        // @ 글작성 정보 가져오기
        //@PathVariable=RUL 경로의 값을 메서드 파라미터로 바인딩
        System.out.println("-----------------------------글정보 보기에요");
        System.out.println(id);
        System.out.println("-----------------------------");
        Optional<RegistrationEntity> viewOptional = registrationService.getaccidentview(id);
        // RegistrationEntity의 id값을 Optional로 받아옴
        // Optional = findById()는 항상 Optional<엔티티>를 반환하는 것이 권장
        RegistrationEntity view = viewOptional.get();
        // 받아온 viewOptional에서 id를 가져와 RegistrationEntity타입으로 view에 저장
        model.addAttribute("view", view);
        // @ 파일이름 가져오기
        List<RegistrationFileEntity> filename = registrationService.getfilename(id);
        model.addAttribute("filename", filename);

        // 댓글보기
        // 순서1) 외래키 id로 테이블조회후
        List<CommentEntity> commentList = commentservice.getComment(id);
        // 순서2) CommentEntity타입으로 가져온 목록을 commentlist에 대입
        model.addAttribute("commenet",commentList);

        // 게시글 조회수 (비동기 처리 로직에서 가져오기)
        int postviewsnum = registrationService.getPostView(id); // 게시글 번호를 파라미터로 전달
        model.addAttribute("postviewsnum",postviewsnum);
        return "accident/accidentview";
    }

    // 글 삭제하기
    @PostMapping("/deleteAccident")
    public String deleteAccident(@RequestParam("id") Integer id, HttpSession session){
        Optional<RegistrationEntity> viewOptional = registrationService.getaccidentview(id);
        RegistrationEntity view = viewOptional.get();
        // 로그인 회원 별명과 글작성회원 별명이 같을때 삭제 가능
        if(session.getAttribute("userAlias").equals(view.getAlias())){
        registrationService.deleteAccident(id);
        }
        return "redirect:/return";
    }

    // 게시글 조회수
    @PostMapping("/Accident/Views")
    @ResponseBody
    public int AccidentViews(@RequestParam("postId") Integer postId){
        return registrationService.PostView(postId); // 게시글 번호를 파라미터로 전달
    }


    // 게시글 수정하기 페이지 채우기용
    @PostMapping("/UpdateAccidentPage")
    public String UpdateAccident(@RequestParam("id") Integer id,
                                 @RequestParam("alias") String alias,
                                 Model model){
        System.out.println();
        Optional<RegistrationEntity> UpdateEntity = registrationService.UpdateAccidentPage(id,alias);
        RegistrationEntity Update = UpdateEntity.get();
        System.out.println("---------------------------게시글 수정하기 여기보자");
        System.out.println(Update.getId());
        System.out.println(Update.getAlias());
        System.out.println("---------------------------게시글 수정하기 여기보자");
        model.addAttribute("Update", Update);
        return "accident/UpdateWriting";
    }

    // 게시글 수정하기
    @PostMapping("/UpdateAccident")
    public String UpdateAccident(@ModelAttribute RegistrationEntity Data){ // 게시글 entity객체로 받기
        RegistrationEntity entity = registrationService.UpdateAccident(Data);
        return "redirect:/accidentview/" + Data.getId();
    }

    // JPA활용 Pageing
    @GetMapping("/return")
    public String list(@RequestParam(defaultValue = "0") int page,  // 현재 페이지 번호 (0부터 시작)
                       @RequestParam(defaultValue = "10") int size, // 한 페이지에 보여줄 개수
                       Model model) {
        System.out.println("----------------페이징확인하기");
        System.out.println(page);
        System.out.println("----------------페이징확인하기");
        // JPA에서 페이징된 결과를 가져옴 (정렬: 생성일자 기준 내림차순)
        Page<RegistrationEntity> pagedResult = registrationRepository.findAll(
                PageRequest.of(page, size, Sort.by("createDate").descending())
                //   PageRequest.of( (클라이언트요청 페이지), (한페이지게시물갯수),
                //   Sort.by(RegistrationEntity의 변수createDate(현재시간기준) ).descending()<-내림차순   )
        );

        // 페이징된 리스트 (content = 현재 페이지의 게시글 목록)
        model.addAttribute("pagedResult", pagedResult);

        // 현재 페이지 번호
        model.addAttribute("currentPage", page);

        // 전체 페이지 수
        model.addAttribute("totalPages", pagedResult.getTotalPages());

        return "accident/accidentmain";
    }

}

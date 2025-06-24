package com.cai.helppsy.accidentBulleinBoard.controller;

import com.cai.helppsy.accidentBulleinBoard.DTO.CommentDTO;
import com.cai.helppsy.accidentBulleinBoard.DTO.RegistrationDTO;
import com.cai.helppsy.accidentBulleinBoard.entity.CommentEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationEntity;
import com.cai.helppsy.accidentBulleinBoard.entity.RegistrationFileEntity;
import com.cai.helppsy.accidentBulleinBoard.repository.RegistrationRepository;
import com.cai.helppsy.accidentBulleinBoard.service.CommentService;
import com.cai.helppsy.accidentBulleinBoard.service.RegistrationLikeService;
import com.cai.helppsy.accidentBulleinBoard.service.RegistrationService;
import com.cai.helppsy.memberManager.SignupEntity;
import com.cai.helppsy.memberManager.signupRepository;
import com.cai.helppsy.memberManager.signupService;
import com.cai.helppsy.tools.Paging;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class RegistrationController {

    private final RegistrationService registrationService;
    private final CommentService commentservice;
    private final RegistrationRepository registrationRepository;
    private final signupRepository signupRepository; // 회원관리

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
    public String writing(@ModelAttribute RegistrationEntity registrationEntity,
                          Model model){
        System.out.println("--------------------사고 게시판 작성글 ");
        System.out.println("위도 :" + registrationEntity.getLatitude());
        System.out.println("경도 :" + registrationEntity.getLongitude());
        System.out.println("--------------------사고 게시판 작성글 ");

        // session.getAttribute("userAlias").toString() 세션에 저장되어있는 별명을 바로 엔티티에 저장
        registrationService.write(registrationEntity); // 작성글 서비스단 보내기
//        registrationService.files(file, registrationEntity); // 사진 서비스단 보내기

        // 회원 정보 저장시키기.
        SignupEntity signupEntity = signupRepository.findByAlias(registrationEntity.getAlias());
        String signupEntityImg = signupEntity.getProfileImage(); // 회원 프로필 이미지

        // 게시글 자료형의 entity 변수 객체를 만들어
        // .findTop1ByAliasOrderByCreateDateDesc = Alias로 조회한 CreateDateDesc가장최신 순으로 Top1 1개만 가져온다

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
    //@PathVariable=RUL 경로의 값을 메서드 파라미터로 바인딩
    // @RequestParam(value = "alias", required = false) String alias
    // = 댓글 작성 commentController부터 인코딩되어 댓글작성자 별칭 넘겨받음
    @GetMapping("/accidentview/{id}")
    public String accidentview(Model model, @PathVariable("id") Integer id,
                               @RequestParam(value = "alias", required = false) String alias) {

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
        // 댓글회원 가져오기 DTO객체로 넘겨줄것임
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity comment : commentList) {
            SignupEntity signup = signupRepository.findByAlias(comment.getAlias());
            String profileImage = (signup != null) ? signup.getProfileImage() : null;
            commentDTOList.add(new CommentDTO(comment, profileImage));
        }
        model.addAttribute("commentDTOList", commentDTOList);

//        // 순서2) CommentEntity타입으로 가져온 목록을 commentlist에 대입
//        model.addAttribute("commenet",commentList);

        // 게시글 조회수 (비동기 처리 로직에서 가져오기)
        int postviewsnum = registrationService.getPostView(id); // 게시글 번호를 파라미터로 전달
        model.addAttribute("postviewsnum",postviewsnum);

        // 회원 프로필 사진 가져오기 (게시글 작성자용)
        SignupEntity signupEntity = signupRepository.findByAlias(view.getAlias());
        model.addAttribute("signupEntity",signupEntity);


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
        Optional<RegistrationEntity> UpdateEntity = registrationService.UpdateAccidentPage(id,alias);
        RegistrationEntity Update = UpdateEntity.get();
        System.out.println("---------------------------게시글 수정하기 여기보자");
        System.out.println(Update.getId());
        System.out.println(Update.getAlias());
        System.out.println(Update.getContent());
        System.out.println(Update.getPreview());
        System.out.println("---------------------------게시글 수정하기 여기보자");
        model.addAttribute("Update", Update);
        return "accident/UpdateWriting";
    }

    // 게시글 수정하기
    @PostMapping("/UpdateAccident")
    public String UpdateAccident(@ModelAttribute RegistrationEntity Data){ // 게시글 entity객체로 받기
        System.out.println("------------------------ 지금 여기확인해라");
        System.out.println("수정글번호 : " + Data.getId());
        System.out.println("수정내용 : " + Data.getContent());
        System.out.println("------------------------ 지금 여기확인해라");
        RegistrationEntity entity = registrationService.UpdateAccident(Data);

        return "redirect:/accidentview/" + Data.getId();
    }

    // JPA활용 Pageing
    @GetMapping("/return")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String accident,  // 카테고리 파라미터 추가
                       Model model) {

        Page<RegistrationEntity> pagedResult;

        if (accident == null || accident.equals("") || accident.equals("전체보기")) {
            // 전체보기: 기존과 동일하게 전체 조회
            pagedResult = registrationRepository.findAll(
                    PageRequest.of(page, size, Sort.by("createDate").descending())
            );
        } else {
            // 특정 카테고리만 조회 (accident 필드가 일치하는 것)
            pagedResult = registrationRepository.findByAccident(
                    accident, PageRequest.of(page, size, Sort.by("createDate").descending())
            );
        }

        // RegistrationEntity를 RegistrationDTO로 변환하면서 profileImage를 추가로 세팅하는 과정
        List<RegistrationDTO> dtoList = pagedResult.stream()   // pagedResult: 기존 Entity 기반 페이징 결과
                .map(entity -> {
                    // entity에서 작성자의 alias(별명)를 추출하여 회원 정보 조회
                    SignupEntity signup = signupRepository.findByAlias(entity.getAlias());

                    // 회원 정보가 존재하면 profileImage를 가져오고, 없으면 null 설정
                    String profileImg = (signup != null) ? signup.getProfileImage() : null;

                    // RegistrationDTO 생성자에 entity 정보와 profileImage를 전달하여 DTO 객체 생성
                    return new RegistrationDTO(entity, profileImg);
                })
                .collect(Collectors.toList());  // 최종적으로 DTO 리스트로 수집

        // 위에서 만든 DTO 리스트를 다시 Page 객체로 감싸기 (기존 페이징 정보 유지)
        Page<RegistrationDTO> dtoPage = new PageImpl<>(
                dtoList,                          // 새로 만든 DTO 리스트
                pagedResult.getPageable(),       // 기존 페이지 요청 정보 (현재 페이지, 사이즈 등)
                pagedResult.getTotalElements()   // 전체 게시글 수 (총 개수 유지)
        );

        model.addAttribute("pagedResult", dtoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("selectedAccident", accident); // 선택값도 넘겨서 셀렉트 박스 유지

        return "accident/accidentmain";
    }
}

package com.cai.helppsy.inquiry.controller;


import com.cai.helppsy.inquiry.entity.Answer;
import com.cai.helppsy.inquiry.entity.Question;
import com.cai.helppsy.inquiry.repository.AnswerRepository;
import com.cai.helppsy.inquiry.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class InquiryController {
    private final AnswerRepository a1;
    private final QuestionRepository q1;

    @GetMapping("inquiry")
    public String inquiry() {
        return "inquiry/inquiry";
    }

    @PostMapping("ddd")
    public String ddd(@RequestParam("writer") String writer,
                      @RequestParam("subject") String subject,
                      @RequestParam("content") String content,
                      @RequestParam("file") MultipartFile file) throws IOException {

        Question question = new Question();
        question.setWriter(writer);
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());

        if (!file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String upload = System.getProperty("user.dir") + "/files/inquiry"; // 현재 경로 + "/upload/"
            String fileName = uuid.toString()+"_"+file.getOriginalFilename();   //fileName에 원래 파일이름을 저장
            File dest = new File(upload,fileName);
            file.transferTo(dest);  //MultipartFile file 객체에 담긴 업로드된 파일을 실제로 서버 디스크에 저장
            System.out.println("__________________");
            System.out.println(dest);
            System.out.println("__________________");
            question.setFile(fileName);
        }
        q1.save(question);
        return "redirect:/inquiry";
    }


    @GetMapping("respondent")   // 문의 답변전체 페이지
    public String listUsers(Model model) {
        List<Question> users = q1.findAll();  // DB에서 모든 사용자 조회
        model.addAttribute("users", users); // 모델에 담기
        return "inquiry/respondent"; // 템플릿 파일명 (userList.html)
    }

    @PostMapping("kkk")
    public String kkk(@RequestParam(value = "action") String action,    //@Requestparam으로 action을 넘겨줌
                      @RequestParam(value = "ids", required = false) List<Integer> ids, //기본값: true를 false로 바꿈
                      @ModelAttribute Answer A,
                      @RequestParam(value = "question.id", required = false) Integer qid) { //question.id를 넘겨줌 변수명 : qid

        if ("delete".equals(action)) {      //action이 delete면
            if (ids != null && !ids.isEmpty()) {    //ids가 빈값이 아니면 존재여부 확인
                q1.deleteAllById(ids);  //해당 id(번호) 행 삭제
            }
        } else if ("answer".equals(action)) {   //action이 answer이면
            A.setCreateDate(LocalDateTime.now());

            // 질문 ID가 전달되었으면 연결해주기
            if (qid != null) {  //qid가 존재하면
                Question question = q1.findById(qid).orElse(null);
                A.setQuestion(question);
            }

            a1.save(A);
        }

        return "redirect:/respondent";
    }


    @PostMapping("delete")
    public String delete(@RequestParam(value = "ids", required = false) List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            q1.deleteAllById(ids);  //해당 ids 삭제
        }
        return "redirect:/respondent";
    }


    @GetMapping("/question/{id}")   //해당 id 문의 자세히 보기 페이지
    public String questionDetail(@PathVariable("id") Integer id, Model model) {
        Question question = q1.findById(id).orElse(null);

        if (question == null) {
            // 존재하지 않는 질문이면 목록으로 리다이렉트
            return "redirect:/respondent";
        }

        List<Answer> answerList = a1.findByQuestionId(id);

        model.addAttribute("question", question);
        model.addAttribute("answers",answerList);
        return "inquiry/question_detail";
    }


}
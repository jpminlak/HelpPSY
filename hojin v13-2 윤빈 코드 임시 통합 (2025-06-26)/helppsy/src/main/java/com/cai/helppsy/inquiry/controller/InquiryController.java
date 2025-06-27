package com.cai.helppsy.inquiry.controller;


import com.cai.helppsy.inquiry.entity.Answer;
import com.cai.helppsy.inquiry.entity.Question;
import com.cai.helppsy.inquiry.repository.AnswerRepository;
import com.cai.helppsy.inquiry.repository.QuestionRepository;
import com.cai.helppsy.note.Note;
import com.cai.helppsy.note.NoteRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
    private final NoteRepository noteRepository;


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

        // 파일이 있을 경우 처리
        if (!file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uploadDir = System.getProperty("user.dir") + "/files/inquiry";
            File dir = new File(uploadDir);

            // 디렉터리 없으면 생성
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 파일 이름 구성 및 저장
            String fileName = uuid + "_" + file.getOriginalFilename();
            File dest = new File(dir, fileName);
            file.transferTo(dest);

            question.setFile(fileName);  // 저장된 파일명 DB에 저장
        }

        q1.save(question);
        return "redirect:/inquiry";
    }


    @GetMapping("respondent")   // 문의 답변전체 페이지
    public String listUsers(@RequestParam(value = "sort",defaultValue = "desc") String sort, Model model) {
        Sort.Direction direction = sort.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        List<Question> users = q1.findAll(Sort.by(direction, "createDate"));

//        List<Question> users = q1.findAll();  // DB에서 모든 사용자 조회
        model.addAttribute("users", users); // 모델에 담기
        model.addAttribute("sort", sort);
        return "inquiry/respondent"; // 템플릿 파일명 (userList.html)
    }

    @PostMapping("kkk")
    public String kkk(@RequestParam(value = "action") String action,
                      @RequestParam(value = "ids", required = false) List<Integer> ids,
                      @ModelAttribute Answer A,
                      @RequestParam(value = "question.id", required = false) Integer qid,
                      HttpSession session) {

        if ("delete".equals(action)) {
            if (ids != null && !ids.isEmpty()) {
                q1.deleteAllById(ids);
            }
        } else if ("answer".equals(action)) {
            A.setCreateDate(LocalDateTime.now());

            if (qid != null) {
                Question question = q1.findById(qid).orElse(null);
                A.setQuestion(question);

                // 답변 저장
                a1.save(A);

                // ===== 쪽지 전송 로직 시작 =====
                if (question != null) {
                    String writerId = question.getWriter(); // 문의 작성자
                    String adminId = (String) session.getAttribute("userId"); // 답변자 (관리자) 아이디

                    // Note 객체 생성
                    Note note = new Note();
                    note.setReceiverId(writerId);
                    note.setSenderId(adminId);
                    note.setSentAt(LocalDateTime.now());
                    note.setTitle("[문의 답변] " + question.getSubject());
                    note.setContent("문의하신 내용: " + question.getContent() + "\n\n답변 내용: " + A.getContent2());
                    note.setRelatedQuestionId(question.getId());

                    noteRepository.save(note); // 쪽지 저장
                }
                // ===== 쪽지 전송 로직 끝 =====
            }
        }

        return "redirect:/question/" + qid;
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
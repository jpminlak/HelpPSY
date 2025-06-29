package com.cai.helppsy.inquiry.controller;

import com.cai.helppsy.inquiry.entity.Answer;
import com.cai.helppsy.inquiry.entity.Question;
import com.cai.helppsy.inquiry.repository.AnswerRepository;
import com.cai.helppsy.inquiry.repository.QuestionRepository;
import com.cai.helppsy.memberManager.SignupEntity;
import com.cai.helppsy.memberManager.signupRepository;
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
import java.util.*;

@RequiredArgsConstructor
@Controller
public class InquiryController {
    private final AnswerRepository a1;
    private final QuestionRepository q1;
    private final NoteRepository noteRepository;
    private final signupRepository signupRepository;


    @GetMapping("inquiry")
    public String inquiry() {
        return "inquiry/inquiry";
    }

    // @PostMapping("ddd") 질문 작성 메서드 수정: HttpSession을 통해 userId와 userAlias를 가져와 저장
    @PostMapping("ddd")
    public String ddd(@RequestParam("subject") String subject,
                      @RequestParam("content") String content,
                      @RequestParam("file") MultipartFile file,
                      HttpSession session) throws IOException {

        String userId = (String) session.getAttribute("userId");
        String userAlias = (String) session.getAttribute("userAlias");

        if (userId == null || userAlias == null) {
            return "redirect:/signInMain"; // 로그인되지 않은 사용자 처리
        }

        Question question = new Question();
        question.setWriter(userAlias);       // 질문 당시 별명 (현재 별칭)
        question.setWriterUserId(userId);    // <-- !!! 핵심: 질문 작성자의 userId 저장 !!!
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());

        // 파일이 있을 경우 처리
        if (!file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uploadDir = System.getProperty("user.dir") + "/files/inquiry";
            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = uuid + "_" + file.getOriginalFilename();
            File dest = new File(dir, fileName);
            file.transferTo(dest);

            question.setFile(fileName);
        }

        q1.save(question);
        return "redirect:/inquiry";
    }


    @GetMapping("respondent")   // 문의 답변전체 페이지
    public String listUsers(@RequestParam(value = "sort",defaultValue = "desc") String sort, Model model) {

        Sort.Direction direction = sort.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        List<Question> questions = q1.findAll(Sort.by(direction, "createDate"));

        // questionWriterAliases 리스트를 모델에 추가하는 로직 (respondent.html에서 사용)
        List<String> currentWriterAliases = new ArrayList<>();
        for (Question question : questions) {
            String aliasToDisplay = question.getWriter(); // 기본은 질문 당시 별명

            // writerUserId를 사용하여 최신 별칭 조회 시도
            if (question.getWriterUserId() != null && !question.getWriterUserId().isEmpty()) {
                SignupEntity writerSignup = signupRepository.findByuserId(question.getWriterUserId());
                if (writerSignup != null) {
                    aliasToDisplay = writerSignup.getAlias(); // 최신 별칭으로 업데이트
                }
            }
            currentWriterAliases.add(aliasToDisplay);
        }

        model.addAttribute("users", questions); // 기존 질문 리스트
        model.addAttribute("currentWriterAliases", currentWriterAliases); // 각 질문에 대한 최신 별칭 리스트
        model.addAttribute("sort", sort);
        return "inquiry/respondent";
    }

    // @PostMapping("kkk") 답변 작성 메서드 수정: HttpSession을 통해 userId와 userAlias를 가져와 저장
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
            String userId = (String) session.getAttribute("userId");
            String userAlias = (String) session.getAttribute("userAlias");

            if (userId == null || userAlias == null) {
                return "redirect:/signInMain"; // 로그인되지 않은 경우 처리
            }

            A.setCreateDate(LocalDateTime.now());

            if (qid != null) {
                Question question = q1.findById(qid).orElse(null);
                A.setQuestion(question);
                A.setRespondent(userAlias);       // 답변 당시 별명 저장
                A.setRespondentUserId(userId);    // <-- !!! 핵심: 답변자 userId 저장 !!!

                a1.save(A); // 답변 저장

                // ===== 쪽지 전송 로직 시작 =====
                if (question != null && question.getWriterUserId() != null && !question.getWriterUserId().isEmpty()) {
                    String writerUserIdForNote = question.getWriterUserId(); // 문의 작성자(질문 엔티티)의 userId
                    String adminId = (String) session.getAttribute("userId"); // 답변자(관리자)의 userId

                    Note note = new Note();
                    note.setReceiverId(writerUserIdForNote); // 쪽지 수신자를 문의 작성자의 userId로 설정
                    note.setSenderId(adminId); // 쪽지 발신자를 답변자(관리자)의 userId로 설정
                    note.setSentAt(LocalDateTime.now());
                    note.setTitle("[문의 답변] " + question.getSubject());
                    note.setContent("문의하신 내용: " + question.getContent()+ "||" + "\n\n답변 내용: " + A.getContent2());
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
            q1.deleteAllById(ids);
        }
        return "redirect:/respondent";
    }

    // @GetMapping("/question/{id}") 질문 상세 보기 메서드 수정: 최신 별칭 조회 로직 강화
    @GetMapping("/question/{id}")
    public String questionDetail(@PathVariable("id") Integer id, Model model) {
        Question question = q1.findById(id).orElse(null);
        if (question == null) {
            return "redirect:/respondent";
        }

        // 1. 질문 작성자의 최신 별칭 조회
        String questionWriterAlias = question.getWriter(); // 기본은 질문 당시 별명
        if (question.getWriterUserId() != null && !question.getWriterUserId().isEmpty()) {
            SignupEntity writerSignup = signupRepository.findByuserId(question.getWriterUserId());
            if (writerSignup != null) {
                questionWriterAlias = writerSignup.getAlias(); // 최신 별칭으로 업데이트
            }
        }
        model.addAttribute("questionWriterAlias", questionWriterAlias);


        // 2. 답변자들의 최신 별칭 조회
        List<Answer> answerList = a1.findByQuestion(question);

        List<String> answerRespondentAliases = new ArrayList<>();
        for (Answer answer : answerList) {
            String respondentAlias = answer.getRespondent(); // 기본은 답변 당시 별명
            if (answer.getRespondentUserId() != null && !answer.getRespondentUserId().isEmpty()) {
                SignupEntity respondentSignup = signupRepository.findByuserId(answer.getRespondentUserId());
                if (respondentSignup != null) {
                    respondentAlias = respondentSignup.getAlias(); // 최신 별칭으로 업데이트
                }
            }
            answerRespondentAliases.add(respondentAlias);
        }
        model.addAttribute("answerRespondentAliases", answerRespondentAliases);


        model.addAttribute("question", question);
        model.addAttribute("answers", answerList);
        return "inquiry/question_detail";
    }
}
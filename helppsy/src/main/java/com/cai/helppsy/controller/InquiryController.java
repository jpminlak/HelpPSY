package com.cai.helppsy.controller;

import com.cai.helppsy.inquiry.Answer;
import com.cai.helppsy.inquiry.AnswerRepository;
import com.cai.helppsy.inquiry.Question;
import com.cai.helppsy.inquiry.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class InquiryController {
    private final AnswerRepository a1;
    private final QuestionRepository q1;

    @GetMapping("inquiry")
    public String inquiry(){
        return "inquiry";
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

        //Answer answer = new Answer();
        //answer.setQuestion(question);
        //answer.setCreateDate(LocalDateTime.now());
        //a1.save(answer);
        if(!file.isEmpty()){
            String upload = System.getProperty("user.dir") + "/upload/"; // 현재 경로 + "/upload/"
            // upload는 파일을 저장할 폴더 위치임
            File uploadPath = new File(upload); //uploadPath는 upload 폴더를 말한다.
            if(!uploadPath.exists()){   // 폴더가 있는지 없는지 확인
                uploadPath.mkdirs();    // 없으면 필요한 경로 모두 포함해서 폴더를 만든다.
            }

            String fileName = file.getOriginalFilename();
            File dest =new File(upload + fileName);
            file.transferTo(dest);

            question.setFile(fileName);
        }
        q1.save(question);
        return "redirect:/inquiry";
    }
}
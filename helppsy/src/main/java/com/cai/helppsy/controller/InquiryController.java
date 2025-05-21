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
    public String ddd(@ModelAttribute Question question){
        question.setCreateDate(LocalDateTime.now());

        q1.save(question);
        //Answer answer = new Answer();
        //answer.setQuestion(question);
        //answer.setCreateDate(LocalDateTime.now());
        //a1.save(answer);

        return "redirect:/inquiry";
    }
}

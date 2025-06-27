package com.cai.helppsy.inquiry.repository;

import com.cai.helppsy.inquiry.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionId(Integer quesionId);
}
// AnswerRepository.java
package com.cai.helppsy.inquiry.repository;

import com.cai.helppsy.inquiry.entity.Answer;
import com.cai.helppsy.inquiry.entity.Question; // Question 엔티티 임포트
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionId(Integer questionId);
    List<Answer> findByQuestion(Question question);
}
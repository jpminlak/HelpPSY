package com.cai.helppsy.inquiry;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    void deleteById(Integer id);
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable);
}

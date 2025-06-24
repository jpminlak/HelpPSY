package com.cai.helppsy.inquiry.repository;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.cai.helppsy.inquiry.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    void deleteById(Integer id);
}

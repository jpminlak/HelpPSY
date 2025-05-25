package com.cai.helppsy.inquiry;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    void deleteById(Integer id);
}

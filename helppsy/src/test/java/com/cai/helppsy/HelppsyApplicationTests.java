package com.cai.helppsy;

import com.cai.helppsy.inquiry.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelppsyApplicationTests {
	@Autowired
	QuestionRepository questionRepository;
	@Test
	void contextLoads() {
		questionRepository.deleteById(13);
	}

}

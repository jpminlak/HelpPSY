package com.test.crud;

import com.test.crud.entity.Question;
import com.test.crud.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class CrudApplicationTests {
	//스프링의 DI 의존성 주입
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("테스트를 왜 하나요");
		q1.setContent("테스트를 진행하는 이유에 대해서 궁금합니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("Jpa에 관하여");
		q2.setContent("Jpa는 무엇이며, 사용하는 이유는 무엇인가요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);

//		List<Question> all = this.questionRepository.findAll();
//		assertEquals(2, all.size());

//		Optional<Question> oq = this.questionRepository.findById(9);
//		if(oq.isPresent()) {
//			Question q = oq.get();
//			assertEquals("테스트를 왜 하나요", q.getSubject());
//		}
//		Question qq = this.questionRepository.findBySubject("테스트를 왜 하나요");
//		System.out.println(qq.getId());
//		System.out.println(qq.getSubject());
//		System.out.println(qq.getContent());
//		System.out.println(qq.getCreateDate());
	}

}

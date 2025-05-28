package com.cai.helppsy;

import com.cai.helppsy.inquiry.Question;
import com.cai.helppsy.inquiry.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class HelppsyApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;


	@Test
	public void MemberList() {
		List<Question> a = questionRepository.findAll();
		List<Question> b = new ArrayList();

//		for(Question aa : a){
//			System.out.println(aa.getId());
//		}
		int P= 2;
		int initialValue = a.size() - 1 -(P-1)*10;
		int conditions = initialValue-9;

		for(int i = initialValue ; i>=conditions; i--){
			b.add(a.get(i));
		}
		for(Question bb : b){
			System.out.println(bb.getId());
		}
	}

	@Test
	public void addList(){
		for(int i=0; i<50; i++){
			Question q = new Question();
			q.setSubject("ddd");
			q.setWriter("ddddd");
			q.setContent("ddddddd");
			questionRepository.save(q);
		}
	}
}

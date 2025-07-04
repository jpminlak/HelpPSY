package com.cai.helppsy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelppsyApplicationTests {
	@Autowired
	private FreeBulletinRepository bulletinRepository;

	@Test
	void contextLoads() {
		// 스프링 컨텍스트가 정상 로딩되는지만 확인
	}

	@Test // 자유게시판의 메인 테스트용
	void pagingTest(){
		Random r = new Random();
		for(int i = 0; i < 500; i++){
			FreeBulletin fb = new FreeBulletin();
			fb.setTitle("aaa"+r.nextInt(1000));
			fb.setViews(r.nextInt(1000));
			fb.setLikes(r.nextInt(1000));
			fb.setWriter("bbb"+r.nextInt(1000));
			fb.setContent("writer"+r.nextInt(1000));
			fb.setCreateDate(LocalDateTime.now());
			fb.setThumbnail("data:image/png;base64,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");
			bulletinRepository.save(fb);
		}
	}
}

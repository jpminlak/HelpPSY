package com.cai.helppsy;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelppsyApplicationTests {
	@Autowired
	private FreeBulletinRepository bulletinRepository;

	@Test
	void contextLoads() {
		System.out.println("______________________________________________");
		System.out.println(System.getProperty("user.dir"));
		System.out.println("______________________________________________");
	}

	@Test
	void pagingTest(){
		for(int i = 0; i < 50; i++){
			FreeBulletin fb = new FreeBulletin();
			fb.setTitle("12345678");
			bulletinRepository.save(fb);
		}
	}

	@Test
	void pagingTest2(){

		System.out.println("__________________");

		System.out.println("__________________");
	}
}

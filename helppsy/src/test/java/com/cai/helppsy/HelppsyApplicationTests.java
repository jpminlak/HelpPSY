package com.cai.helppsy;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

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
		Random r = new Random();
		for(int i = 0; i < 50; i++){
			FreeBulletin fb = new FreeBulletin();
			fb.setTitle("aaa"+r.nextInt(1000));
			fb.setViews(r.nextInt(1000));
			fb.setLikes(r.nextInt(1000));
			bulletinRepository.save(fb);
		}
	}

	@Test
	void pagingTest2(){

		System.out.println("__________________");

		System.out.println("__________________");
	}
}

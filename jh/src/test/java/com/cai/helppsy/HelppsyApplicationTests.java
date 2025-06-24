package com.cai.helppsy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelppsyApplicationTests {
	@Autowired
	private BulletinRepository bulletinRepository;

	@Test
	void contextLoads() {
		System.out.println("______________________________________________");
		System.out.println(System.getProperty("user.dir"));
		System.out.println("______________________________________________");
	}

	@Test
	void pagingTest(){
		for(int i = 0; i < 100; i++){
			bulletinRepository.save(new Bulletin());
		}
	}

	@Test
	void pagingTest2(){

		System.out.println("__________________");

		System.out.println("__________________");
	}
}

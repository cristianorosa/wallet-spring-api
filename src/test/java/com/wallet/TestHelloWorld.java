package com.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestHelloWorld {
	
	@Test
	void testHelloWorld() {
		var num = 1;
		assertEquals(1, num);
	}

}

package com.wallet.auth.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.auth.entity.User;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {
	
	private static final String EMAIL = "test@test.com";
	
	@Autowired
	UserRepository repo;
	
	@BeforeEach
	void setUp() {
		User u = new User();
		u.setName("teste123");
		u.setPassword("teste123");
		u.setEmail(EMAIL);
		
		User response = repo.save(u);
		assertNotNull(response);
	}
	
	@AfterEach
    void tearDown() {
		repo.deleteAll();
    }
	
	@Test
	void testSave() {
		// arrange - cenario
		User u = new User();
		u.setName("teste124");
		u.setPassword("teste124");
		u.setEmail("teste124@test.com");
		
		// ACT - acao
		User response = repo.save(u);
		
		// verificacao
		assertNotNull(response);
	}
	
	@Test
	void testFindByEmail() {
		Optional<User> response = repo.findByEmail(EMAIL);
		
		assertTrue(response.isPresent());
		assertEquals(EMAIL, response.get().getEmail());
	}
	
}

package com.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.auth.entity.User;
import com.wallet.auth.repository.UserRepository;
import com.wallet.auth.service.UserService;
import com.wallet.exception.BusinessException;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
	
	static final String NAME = "test1";
	static final String PASSWORD = "test123";
	static final String EMAIL = "test@test.com";
	static final String URL = "/user";

	@Autowired
	UserService service;
	
	@Autowired
	UserRepository repo;

	@BeforeEach
	void setUp() {
		User user1 = repo.save(getMockUser());
		assertNotNull(user1);
	}
	
	@AfterEach
    void tearDown() {
		repo.deleteAll();
    }
	
	@Test
	void testFindByEmail() {
		Optional<User> user = service.findByEmail("test@test.com");
		assertTrue(user.isPresent());
	}
	
	@Test
	void testSaveUser() throws BusinessException {
		repo.deleteAll();
		var user2 = service.save(getMockUser());
		assertNotNull(user2);
	}
	
	@Test
	void testSaveSameUser() {
		Throwable exception = assertThrows(BusinessException.class, () -> {
			var user2 = service.save(getMockUser());
			assertNotNull(user2);
	    });
		assertEquals("Usuario já cadastrado com mesmo nome", exception.getMessage());
	}
	
	public User getMockUser() {		
		User user = new User();
		user.setEmail(EMAIL);
		user.setName(NAME);
		user.setPassword(PASSWORD);
		return user;
	}

}
	
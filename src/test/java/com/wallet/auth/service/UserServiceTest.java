package com.wallet.auth.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.auth.entity.User;
import com.wallet.auth.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

	@MockBean
	UserRepository repo;
	
	@Autowired
	UserService service;

	@Test
	void testFindByEmail() {
		Optional<User> user = service.findByEmail("email@test.com");
		
		assertTrue(user.isPresent());
	}
	
	@BeforeEach
	void setUp() {
		BDDMockito.given(repo.findByEmail(Mockito.anyString())).willReturn(Optional.of(new User()));
	}

}
	
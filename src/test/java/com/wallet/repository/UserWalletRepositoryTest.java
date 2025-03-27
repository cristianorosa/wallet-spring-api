package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.auth.entity.User;
import com.wallet.auth.repository.UserRepository;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;

@SpringBootTest
@ActiveProfiles("test")
class UserWalletRepositoryTest {
	
	static final Long ID = 1L;
	
	@Autowired
	UserWalletRepository repo;
	
	@Autowired
	WalletRepository repoWallet;
	
	@Autowired
	UserRepository repoUser;
	
	private Wallet wallet;
	private User user;
	
	@BeforeEach
	void init() {
		wallet = repoWallet.save(new Wallet(ID, "carteira test 1", new BigDecimal(2155.55)));
		assertNotNull(wallet);
		
		user = repoUser.save(new User(ID, "user_test1","test@email.com", "test@123"));
		assertNotNull(user.getPassword());
	}
	
	@AfterEach
    void tearDown() {
		repo.deleteAll();
		repoUser.deleteAll();
		repoWallet.deleteAll();
    }
	
	@Test
	void testSave() {		
		UserWallet userWallet = new UserWallet();
		userWallet.setId(ID);
		userWallet.setUsers(user);
		userWallet.setWallet(wallet);
		
		UserWallet uWallet = repo.save(userWallet);
		assertNotNull(uWallet);
		assertEquals(ID, uWallet.getId());
	}
}

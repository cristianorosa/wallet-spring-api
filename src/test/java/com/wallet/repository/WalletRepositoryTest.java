package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entity.Wallet;

@SpringBootTest
@ActiveProfiles("test")
class WalletRepositoryTest {
	
	@Autowired
	WalletRepository repo;
	
	@BeforeEach
	void init() {
		Wallet w = new Wallet(1l, "carteira test 1", new BigDecimal(2155.55));
		Wallet wallet = repo.save(w);
		assertNotNull(wallet);
	}	
	
	@AfterEach
    void tearDown() {
		repo.deleteAll();
    }
	
	@Test
	void testSave() {
		Wallet w = new Wallet(1l, "carteira test 1", new BigDecimal(2155.55));
		Wallet response = repo.save(w);
		assertNotNull(response);
	}
			
}

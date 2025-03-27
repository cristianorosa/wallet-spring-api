package com.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entity.Wallet;

@SpringBootTest
@ActiveProfiles("test")
class WalletServiceTest {

	@Autowired
	WalletService service;
		
	static final String NAME = "Carteira Teste";
	static final BigDecimal VALUE = BigDecimal.valueOf(65);
	
	@Test
	void save() {
		Wallet wallet = service.save(new Wallet(null, NAME, VALUE));	
		assertNotNull(wallet);
		assertEquals(NAME, wallet.getName());
		assertEquals(VALUE, wallet.getValue());
	}
	
	@Test
	void findByName() {
		Wallet wallet = service.save(new Wallet(null, "Carteira test", VALUE));	
		Optional<Wallet> w = service.findByName("Carteira test");
		
		assertNotNull(w);
		assertEquals("Carteira test", wallet.getName());
		assertEquals(VALUE, wallet.getValue());
	}
	
}
	
package com.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.auth.entity.User;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;
import com.wallet.repository.UserWalletRepository;

@SpringBootTest
@ActiveProfiles("test")
class UserWalletServiceTest {
	
	static final Long ID = 1L;
	
	static final String NAME = "test1";
	static final String PASSWORD = "test123";
	static final String EMAIL = "test@test.com";

	@MockBean
	UserWalletRepository repo;
	
	@Autowired
	UserWalletService service;

	@Test
	void testSave() {
		BDDMockito.given(repo.save(Mockito.any(UserWallet.class))).willReturn(getMockUserWallet());
		
		UserWallet userWallet = service.save(getMockUserWallet());
		
		assertNotNull(userWallet);
		assertEquals(ID, userWallet.getWallet().getId());
		assertEquals(NAME, userWallet.getUsers().getName());		
	}
	
	@Test
	void testFindByUserIdAndWalletId() {
		BDDMockito.given(repo.findByUsersIdAndWalletId(Mockito.anyLong(), Mockito.anyLong())).willReturn(Optional.of(getMockUserWallet()));
		
		Optional<UserWallet> userWallet = service.findByUserIdAndWalletId(ID, ID);
		
		assertNotNull(userWallet);
		assertEquals(ID, userWallet.get().getWallet().getId());
	}
	
	private UserWallet getMockUserWallet() {
		UserWallet uw = new UserWallet();
		uw.setUsers(getMockUser());
		uw.setWallet(getMockWallet());
		return uw;
	}


	private Wallet getMockWallet() {
		return new Wallet(1L, "wallet_test", new BigDecimal(45));
	}

	public User getMockUser() {		
		User user = new User();
		user.setEmail(EMAIL);
		user.setName(NAME);
		user.setPassword(PASSWORD);
		return user;
	}

}
	
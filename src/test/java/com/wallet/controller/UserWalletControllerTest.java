package com.wallet.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.auth.entity.User;
import com.wallet.auth.repository.UserRepository;
import com.wallet.auth.service.TokenService;
import com.wallet.dto.UserWalletDTO;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;
import com.wallet.repository.WalletRepository;
import com.wallet.service.UserWalletService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserWalletControllerTest {

	private static final Long ID = 1L;
	private static final Long WALLET = 1L;
	private static final Long USER = 1L;
	private static final String URL = "/user-wallet";

	@MockBean
	UserWalletService service;
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
	WalletRepository repoWallet;
	
	@Autowired
	UserRepository repoUser;
	
	@Autowired
    TokenService tokenService;
	
	Wallet wallet;
	User user;
	String token;
	
	@BeforeEach
	void setUp() {
		wallet = repoWallet.save(new Wallet(1L, "carteira test 1", new BigDecimal(2155.55)));
		assertNotNull(wallet);
		
		user = repoUser.save(new User(1L, "test@123", "user_test1","test@email.com"));
		assertNotNull(user);
		
		token = tokenService.gerarToken(user);
		assertNotNull(token);
	}
			
	@AfterEach
    void tearDown() {
		repoUser.deleteAll();
		repoWallet.deleteAll();
    }
	
	@Test
	void testSave() throws Exception {
		BDDMockito.given(service.save(Mockito.any(UserWallet.class))).willReturn(new UserWallet(ID, USER, WALLET));
		
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.header("Authorization", token)
				.content(getJsonPayLoad(ID, USER, WALLET))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.id").value(ID))
			.andExpect(jsonPath("$.data.users").value(USER))
			.andExpect(jsonPath("$.data.wallet").value(WALLET));			
	}
	
	public String getJsonPayLoad(Long id, Long user, Long wallet) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(new UserWalletDTO(id, user, wallet));
	}
}

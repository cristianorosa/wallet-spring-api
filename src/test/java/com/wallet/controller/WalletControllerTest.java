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
import com.wallet.dto.WalletDTO;
import com.wallet.entity.Wallet;
import com.wallet.service.WalletService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class WalletControllerTest {

	private static final Long ID = 1L;
	private static final String NAME = "test1";
	private static final BigDecimal VALUE = BigDecimal.valueOf(1.555);
	private static final String URL = "/wallet";
	
	private static final String LOGIN = "Admin";
	private static final String PASSWORD = "admin@123";
	
	@MockBean
	WalletService service;

	@Autowired
	MockMvc mvc;
	
	@Autowired
	UserRepository repoUser;
	
	@Autowired
    TokenService tokenService;
	
	User user;
	String token;
	
	@BeforeEach
	void setUp() {
		user = repoUser.save(new User(1l, LOGIN, PASSWORD, "admin@inverter.com.br"));
		assertNotNull(user);
		
		token = tokenService.gerarToken(user);
		assertNotNull(token);
	}
	
	@AfterEach
    void tearDown() {
		repoUser.deleteAll();
    }
	
	@Test
	void testSave() throws Exception {				
		BDDMockito.given(service.save(Mockito.any(Wallet.class))).willReturn(getMockWallet());
		
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.header("Authorization", token)
				.content(getJsonPayLoad(ID, NAME, VALUE))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.id").value(ID))
			.andExpect(jsonPath("$.data.name").value(NAME))
			.andExpect(jsonPath("$.data.value").value(VALUE));
	}
	
	@Test
	void testSaveNullWalletValue() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.header("Authorization", token)
				.content(getJsonPayLoad(ID, NAME, null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0]").value("Insira um valor para a carteira"));
	}
	
	@Test
	void testSaveNullWalletName() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.header("Authorization", token)
				.content(getJsonPayLoad(ID, null, VALUE))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0]").value("O nome não pode ser nulo"));
	}

	Wallet getMockWallet() {		
		return new Wallet(ID, NAME, VALUE);
	}

	String getJsonPayLoad(Long id, String name, BigDecimal value) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(new WalletDTO(id, name, value));
	}
}

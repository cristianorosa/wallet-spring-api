package com.wallet.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.auth.dto.LoginDTO;
import com.wallet.auth.entity.User;
import com.wallet.auth.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest {
	
	static final String URL = "/login";
	static final String LOGIN = "Admin";
	static final String PASSWORD = "admin@123";

	@Autowired
	MockMvc mvc;
	
	@MockBean
	UserRepository userRepo;
	
	@MockBean
	AuthenticationManager authManager;
	
	@MockBean
	Authentication authenticate;
	
	UsernamePasswordAuthenticationToken userToken;
	User usuario;
	
	@BeforeEach
	void setUp() {
		Authentication authentic = new Authentication() {
			private static final long serialVersionUID = 1L;
			public String getName()	{return null;}
			public Collection<? extends GrantedAuthority> getAuthorities() {return null;}
			public Object getCredentials() {return null;}
			public Object getDetails() {return null;}
			public Object getPrincipal() {return getMockUser().get();}
			public boolean isAuthenticated() {return false;}
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException { /* document why this method is empty */ }
		};
		
		userToken = new UsernamePasswordAuthenticationToken(LOGIN, PASSWORD);
		usuario = (User) authentic.getPrincipal();
		
		BDDMockito.given(userRepo.findByName(Mockito.anyString())).willReturn(getMockUser());
		BDDMockito.given(authManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).willReturn(authenticate);
		BDDMockito.given(authenticate.getPrincipal()).willReturn(usuario);
	}
	
	@Test
	void testLogin() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.content(getJsonPayLoad())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isAccepted())
			.andExpect(jsonPath("$.data.login").value(LOGIN))
			.andExpect(jsonPath("$.data.password").value(PASSWORD))
			.andExpect(jsonPath("$.data.token").exists());
	}
	
	@Test
	void testLoginWithErrors() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.content(getJsonPayLoadNullName())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	void testLoginWithExceptionAndNullUser() throws Exception {
		BDDMockito.given(authenticate.getPrincipal()).willReturn(null);
		BDDMockito.given(userRepo.findByName(Mockito.anyString())).willReturn(Optional.ofNullable(null));
		
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.content(getJsonPayLoad())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0]").value("Access denied, user not found!"));
	}
	
	@Test
	void testLoginWithExceptionGenric() throws Exception {
		BDDMockito.given(authenticate.getPrincipal()).willReturn(null);
		
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.content(getJsonPayLoad())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0]").value("Cannot invoke \"com.wallet.auth.entity.User.getUsername()\" because \"usuario\" is null"));
	}

	private String getJsonPayLoad() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(new LoginDTO(LOGIN, PASSWORD, null));
	}
	
	private String getJsonPayLoadNullName() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(new LoginDTO(null, PASSWORD, null));
	}

	private Optional<User> getMockUser() {
		return Optional.of(new User(1l, LOGIN, "admin@inverter.com.br", PASSWORD));
	}
}

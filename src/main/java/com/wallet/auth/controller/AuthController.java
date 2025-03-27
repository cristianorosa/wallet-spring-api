package com.wallet.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.auth.dto.LoginDTO;
import com.wallet.auth.entity.User;
import com.wallet.auth.repository.UserRepository;
import com.wallet.auth.service.TokenService;
import com.wallet.util.Response;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
    private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/login")
    public ResponseEntity<Response<LoginDTO>> login(@Valid @RequestBody LoginDTO login, BindingResult result) {
		var response = new Response<LoginDTO>();
		var user = this.userRepo.findByName(login.login());
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		try {
			var userToken = new UsernamePasswordAuthenticationToken(login.login(), login.password());
			var authenticate = authManager.authenticate(userToken);
			var usuario = (User) authenticate.getPrincipal();
			
			response.setData(new LoginDTO(login.login(), login.password(), tokenService.gerarToken(usuario)));
		} catch (Exception e) {
			if (user.isEmpty()) {
				response.getErrors().add("Access denied, user not found!");
			} else {
				response.getErrors().add(e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }	
}

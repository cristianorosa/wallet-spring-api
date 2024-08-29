package com.wallet.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.auth.dto.UserDTO;
import com.wallet.auth.entity.User;
import com.wallet.auth.service.UserService;
import com.wallet.exception.BusinessException;
import com.wallet.util.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<Response<UserDTO>> create(@Valid @RequestBody UserDTO userDto, BindingResult result) {
		Response<UserDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		try {
			User user = service.save(new User(userDto));
			response.setData(buildUserDTO(user));
		} catch (BusinessException e) {
			response.getErrors().add(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	public UserDTO buildUserDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		return dto;
	}

}

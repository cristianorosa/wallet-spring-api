package com.wallet.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginDTO(
		
	@NotEmpty(message = "Invalid Login! Login cannot be empty or null!")
	String login,
	@NotEmpty(message = "Invalid Login! Password cannot be empty or null!")
	String password,
	String token) {
}
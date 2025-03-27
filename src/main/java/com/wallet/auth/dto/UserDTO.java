package com.wallet.auth.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class UserDTO {

	private Long id;

	@Length(min = 3, max = 50, message = "O nome deve conter entre 3 e 50 caracteres")
	@NotNull(message = "O campo 'name' não deve ser nulo")
	private String name;
	
	@Email(message = "Email inválido")
	@NotNull(message = "O campo 'email' não deve ser nulo")
	private String email;
	
	@Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	@NotNull(message = "O campo 'password' não deve ser nulo")
	private String password;
}

package com.wallet.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WalletDTO (

	Long id,

	@NotNull(message="O nome não pode ser nulo")
	@Size(min = 3, message="O nome deve ter no mínimo 3 caracteres")
	String name,

	@NotNull(message="Insira um valor para a carteira")
	BigDecimal value) {}

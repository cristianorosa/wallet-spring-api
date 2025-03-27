package com.wallet.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wallet.entity.Wallet;

import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletDTO {

	private Long id;

	@Length(min = 3, message="O nome deve ter no mínimo 3 caracteres")
	@NotNull(message="O nome não pode ser nulo")
	private String name;

	@NotNull(message="Insira um valor para a carteira")
	private BigDecimal value;

	public WalletDTO() {}

	public WalletDTO(Long id, String name, BigDecimal value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public WalletDTO(Wallet wallet) {
		this.id = wallet.getId();
		this.name = wallet.getName();
		this.value = wallet.getValue();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}

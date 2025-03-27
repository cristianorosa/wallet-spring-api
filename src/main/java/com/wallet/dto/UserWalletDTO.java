package com.wallet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wallet.entity.UserWallet;

import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWalletDTO {

	private Long id;

	@NotNull(message = "informe o id da carteira")
	private Long wallet;

	@NotNull(message = "informe o id do usuário")
	private Long users;
	
	public UserWalletDTO() {}

	public UserWalletDTO(Long id, @NotNull(message = "informe o id da carteira") Long wallet,
			@NotNull(message = "informe o id do usuário") Long users) {
		super();
		this.id = id;
		this.wallet = wallet;
		this.users = users;
	}

	public UserWalletDTO(UserWallet userWallet) {
		this.id = userWallet.getId();
		this.wallet = userWallet.getWallet().getId();
		this.users = userWallet.getUsers().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWallet() {
		return wallet;
	}

	public void setWallet(Long wallet) {
		this.wallet = wallet;
	}

	public Long getUsers() {
		return users;
	}

	public void setUsers(Long users) {
		this.users = users;
	}

}

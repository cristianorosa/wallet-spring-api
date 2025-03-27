package com.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wallet.dto.WalletDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "WALLET")
public class Wallet implements Serializable {

	private static final long serialVersionUID = 1693850165739564098L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String name;
	
	@Column(name = "wallet_value")
	private BigDecimal value;

	public Wallet() {
	}

	public Wallet(@Valid Long id, String name, BigDecimal value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public Wallet(@Valid WalletDTO dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.value = dto.getValue();
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

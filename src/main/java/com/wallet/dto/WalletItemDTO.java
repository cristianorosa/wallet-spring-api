package com.wallet.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class WalletItemDTO {

	private Long id;

	@NotNull(message = "Insira o id da carteira")
	private Long wallet;

	@NotNull(message = "Informe uma data")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Bazil/East")
	private Date date;

	@NotNull(message = "Informe um tipo")
	@Pattern(regexp = "^(ENTRADA|SAIDA)$", message = "Para o tipo somente são aceitos os valores ENTRADA ou SAIDA")
	private String type;

	@NotNull(message = "Informe uma descrição")
	@Length(min = 5, message = "A descrição deve ter no mínimo 5 caracteres")
	private String description;

	@NotNull(message = "Informe um valor")
	private BigDecimal value;

	public WalletItemDTO() {
	}

	public WalletItemDTO(Long id, @NotNull(message = "Insira o id da carteira") Wallet wallet,
			@NotNull(message = "Informe uma data") Date date,
			@NotNull(message = "Informe um typo") @Pattern(regexp = "^(ENTRADA|SAIDA)$", message = "Para o tipo somente são aceitos os valores ENTRADA ou SAIDA") String type,
			@NotNull(message = "Informe uma descrição") String description,
			@NotNull(message = "Informe um valor") BigDecimal value) {
		super();
		this.id = id;
		this.wallet = wallet.getId();
		this.date = date;
		this.type = type;
		this.description = description;
		this.value = value;
	}

	public WalletItemDTO(WalletItem e) {
		this.id = e.getId();
		this.wallet = e.getWallet().getId();
		this.date = e.getDate();
		this.type = e.getType().getValue();
		this.description = e.getDescription();
		this.value = e.getValue();
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}

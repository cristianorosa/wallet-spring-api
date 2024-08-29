package com.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.wallet.dto.WalletItemDTO;
import com.wallet.enums.TypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "WALLET_ITENS")
@Getter
@Setter
public class WalletItem implements Serializable {

	private static final long serialVersionUID = -5920493416299280592L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "wallet", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Wallet wallet = new Wallet();

	@NotNull
	private Date date;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TypeEnum type;

	@NotNull
	private String description;

	@Column(name = "iten_value", nullable = false)
	private BigDecimal value;

	public WalletItem() {}

	public WalletItem(Wallet wallet, @Valid Date date, @Valid TypeEnum type, @Valid String description,
			@Valid BigDecimal value, @Valid Long id) {
		this.id = id;
		this.date = date;
		this.type = type;
		this.description = description;
		this.value = value;
		this.wallet = wallet;
	}

	public WalletItem(Wallet wallet, @Valid Date date, @Valid TypeEnum type, @Valid String description,
			@Valid BigDecimal value) {
		this.date = date;
		this.type = type;
		this.description = description;
		this.value = value;
		this.wallet = wallet;
	}

	public WalletItem(@Valid WalletItemDTO dto) {
		this.id = dto.getId();
		this.date = dto.getDate();
		this.type = TypeEnum.getEnum(dto.getType());
		this.description = dto.getDescription();
		this.value = dto.getValue();
		this.wallet.setId(dto.getWallet());
	}
	
}

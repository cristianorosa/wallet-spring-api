package com.wallet.service;

import java.util.Optional;

import com.wallet.entity.Wallet;

public interface WalletService {
	
	Wallet save(Wallet w);

	Optional<Wallet> findByName(String string);	
	
}

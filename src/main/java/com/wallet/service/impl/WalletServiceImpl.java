package com.wallet.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.entity.Wallet;
import com.wallet.repository.WalletRepository;
import com.wallet.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {
	
	@Autowired
	WalletRepository repo;

	@Override
	public Wallet save(Wallet w) {
		return repo.save(w);
	}

	@Override
	public Optional<Wallet> findByName(String name) {
		return repo.findByName(name);
	}

}

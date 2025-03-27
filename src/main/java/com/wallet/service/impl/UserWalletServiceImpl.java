package com.wallet.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.entity.UserWallet;
import com.wallet.repository.UserWalletRepository;
import com.wallet.service.UserWalletService;

@Service
public class UserWalletServiceImpl implements UserWalletService {
	
	@Autowired
	UserWalletRepository repo;

	@Override
	public UserWallet save(UserWallet u) {		
		return repo.save(u);
	}

	@Override
	public Optional<UserWallet> findByUserIdAndWalletId(Long user, long wallet) {
		return repo.findByUsersIdAndWalletId(user, wallet);
	}
}

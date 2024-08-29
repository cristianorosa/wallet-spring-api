package com.wallet.service;

import java.util.Optional;

import com.wallet.entity.UserWallet;

public interface UserWalletService {

	UserWallet save(UserWallet u);
	Optional<UserWallet> findByUserIdAndWalletId(Long user, long wallet);
}

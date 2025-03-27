package com.wallet.auth.service;

import java.util.Optional;

import com.wallet.auth.entity.User;
import com.wallet.exception.BusinessException;

public interface UserService {
	
	User save(User u) throws BusinessException;
	Optional<User> findByEmail(String email);
	Optional<User> findByName(String name);
	
}

package com.wallet.auth.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.wallet.auth.entity.User;
import com.wallet.auth.repository.UserRepository;
import com.wallet.auth.service.UserService;
import com.wallet.exception.BusinessException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repo;
		
	@Override
	public User save(User u) throws BusinessException {
		try {
			return repo.save(u);
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException("Usuario já cadastrado com mesmo nome"); 
		}
	} 

	@Override
	public Optional<User> findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public Optional<User> findByName(String name) {
		return repo.findByName(name);
	}
};

package com.wallet.auth;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.wallet.auth.entity.User;
import com.wallet.auth.service.UserService;

@Component
public class Auth {

	static UserService service;
	
	Auth(UserService service) {
		Auth.service = service;
	}

	public static Long getAutenticatedUserId() {
		try {
			@SuppressWarnings("unchecked")
			Optional<User> usr =  ((Optional<User>) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			Optional<User> user = service.findByName(usr.isPresent() ? usr.get().getName() : null);
			
			if (user.isPresent()) {
				return user.get().getId();
			} 
			
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}

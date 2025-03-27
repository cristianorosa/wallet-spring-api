package com.wallet.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.wallet.auth.repository.UserRepository;
import com.wallet.auth.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterToken extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)	throws ServletException, IOException {
		var authorizationHeader = req.getHeader("Authorization");
		try {
			if (authorizationHeader != null) {
				var token = authorizationHeader.replace("Bearer ", "");
				var subject = tokenService.getSubject(token);
				var user = userRepo.findByName(subject);
				
				if (user.isPresent()) { 
					var authentication = new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);	
					req.setAttribute("authentication","authenticated");
				}
			} else {
				req.setAttribute("error", "Access denied! You must be authenticated in the system to access the requested URL");
			}
		} catch (TokenExpiredException e) {
			req.setAttribute("expired", e.getMessage());
		} catch (SignatureVerificationException | JWTDecodeException e) {
			req.setAttribute("error", e.getMessage());
		} 
		chain.doFilter(req, res);
	}
}
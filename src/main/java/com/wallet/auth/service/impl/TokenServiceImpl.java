package com.wallet.auth.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wallet.auth.entity.User;
import com.wallet.auth.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Value("${jwt.expiration.min}")
	private Integer expiration;
	
	@Value("${jwt.secret}")
	private String secret;
	
	static final String ISSUE = "WalletApi";

	@Override
    public String gerarToken(User usuario) {
        return JWT.create()
                .withIssuer(ISSUE)
                .withSubject(usuario.getUsername())
                .withClaim("id", usuario.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(expiration)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256(secret));
    }

	@Override
    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(ISSUE)
                .build().verify(token).getSubject();

    }
}
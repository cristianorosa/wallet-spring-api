package com.wallet.auth.service;

import com.wallet.auth.entity.User;

public interface TokenService {
			
    public String gerarToken(User usuario);
    public String getSubject(String token);
}
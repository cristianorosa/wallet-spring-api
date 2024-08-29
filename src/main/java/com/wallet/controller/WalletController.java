package com.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.WalletDTO;
import com.wallet.entity.Wallet;
import com.wallet.service.WalletService;
import com.wallet.util.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping("wallet")
public class WalletController {

	@Autowired
	WalletService service;
	
	@PostMapping
	public ResponseEntity<Response<WalletDTO>> create(@Valid @RequestBody WalletDTO dto, BindingResult result) {
		
		Response<WalletDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));
			
			return ResponseEntity.badRequest().body(response);
		}
			
		Wallet wallet = service.save(new Wallet(dto));
		response.setData(new WalletDTO(wallet));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
}

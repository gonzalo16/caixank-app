package com.ifragodevs.caixank_app.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.service.AccountService;
import com.ifragodevs.caixank_app.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
		
	private final UserService userService;
	
	private final AccountService accountService;
	
	@PostMapping(value = "user")
	public ResponseEntity<?> getUserInfo(){
		
		 // Obtiene el usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = authentication.getName();
        
		return ResponseEntity.ok(userService.getUserInfo(username));
	}
	
	@PostMapping(value = "account")
	public ResponseEntity<?> getAccountInfo(){
		// Obtiene el usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = authentication.getName();
        
        return ResponseEntity.ok(userService.getAccountMainInfo(username));
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<?> getAccount(@PathVariable Integer id){

		Optional<Account> account = accountService.findById(id);
		if(account.isPresent()) {
			return ResponseEntity.ok(account.orElseThrow());
		}
		return ResponseEntity.notFound().build();
	}
}

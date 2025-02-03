package com.ifragodevs.caixank_app.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ifragodevs.caixank_app.dto.AccountCreateDTO;
import com.ifragodevs.caixank_app.dto.TransferDTO;
import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.entity.User;
import com.ifragodevs.caixank_app.exceptions.AccountNotFoundException;
import com.ifragodevs.caixank_app.exceptions.InvalidTransactionException;
import com.ifragodevs.caixank_app.exceptions.UserNotFoundException;
import com.ifragodevs.caixank_app.service.AccountService;
import com.ifragodevs.caixank_app.service.AuthService;
import com.ifragodevs.caixank_app.service.TransactionService;
import com.ifragodevs.caixank_app.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	private final UserService userService;

	private final TransactionService transactionService;

	private final AuthService authService;
	
	// Debemos crear una cuenta nueva para el usuario. El account number de la
	// cuenta nueva sera el de la cuenta principal
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody AccountCreateDTO request) {

		// Obtiene el usuario autenticado desde el contexto de seguridad
		Authentication authentication = authService.getAuth();

		String username = authentication.getName();
		Optional<User> user = userService.findByUsername(username);

		Optional<Account> account = accountService.findByAccountNumber(UUID.fromString(request.getAccountNumber()));
		Account newAccount = Account.builder().accountNumber(account.get().getAccountNumber())
				.accountType(request.getAccountType()).balance(0.0).user(user.orElseThrow()).build();
		accountService.save(newAccount);

		return ResponseEntity.ok("New account added successfully for user");
	}

	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestBody Double request) {
		// Obtiene el usuario autenticado desde el contexto de seguridad
		Authentication authentication = authService.getAuth();

		String username = authentication.getName();

		User user = userService.findByUsername(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado"));

		Account account = accountService.findByUserId(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cuenta no encontrada"));

		transactionService.save(account,request);

		return ResponseEntity.ok("Cash deposited successfully " + request);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<?> withdraw(@RequestBody Double amount) {
		// Obtiene el usuario autenticado desde el contexto de seguridad
		Authentication authentication = authService.getAuth();

		String username = authentication.getName();
		User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

		transactionService.retirar(amount, user.getId());
		return ResponseEntity.ok("Cash withdrawn successfully");
	}

	@PostMapping("/fund-transfer")
	public ResponseEntity<?> fundTransfer(@RequestBody TransferDTO request) {
		// Obtiene el usuario autenticado desde el contexto de seguridad
		Authentication authentication = authService.getAuth();

		String username = authentication.getName();
		User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
				
		Account accountOrigin = accountService.findByUserId(user.getId()).orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada"));
		Account accountTarget = accountService.findByAccountNumber(UUID.fromString(request.getAccountNumber())).orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada"));

		if(request.getAmount() > accountOrigin.getBalance()) {
			return ResponseEntity.badRequest().body("No dispones de saldo suficiente");
		}else {
			transactionService.fundTransfer(request.getAmount(), accountOrigin, accountTarget);
		}
		
		return ResponseEntity.ok("Transferencia echa");
	}
}

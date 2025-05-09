package com.ifragodevs.caixank_app.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.ifragodevs.caixank_app.service.AccountService;
import com.ifragodevs.caixank_app.service.AuthService;
import com.ifragodevs.caixank_app.service.TransactionService;
import com.ifragodevs.caixank_app.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
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

		User user = getAuthentication();

		// Debemos de buscar la cuenta por la cuenta principal
		Account account = accountService.findByAccountNumber(UUID.fromString(request.getAccountNumber()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

		Account newAccount = Account.builder().accountNumber(account.getAccountNumber())
				.accountType(request.getAccountType()).balance(0.0).user(user).build();
		accountService.save(newAccount);

		return ResponseEntity.ok("New account added successfully for user");
	}

	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestBody Double request) {
		
		User user = getAuthentication();

		Account account = accountService.findMainAcountByUserId(user.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

		transactionService.save(account, request);

		return ResponseEntity.ok("Cash deposited successfully " + request);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<?> withdraw(@RequestBody Double amount) {

		User user = getAuthentication();

		transactionService.retirar(amount, user.getId());
		return ResponseEntity.ok("Cash withdrawn successfully");
	}

	@PostMapping("/fund-transfer")
	public ResponseEntity<?> fundTransfer(@RequestBody TransferDTO request) {

		User user = getAuthentication();

		Account accountOrigin = accountService.findByUserId(user.getId())
				.orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada"));
		Account accountTarget = accountService.findByAccountNumber(UUID.fromString(request.getAccountNumber()))
				.orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada"));

		if (request.getAmount() > accountOrigin.getBalance()) {
			return ResponseEntity.badRequest().body("No dispones de saldo suficiente");
		} else {
			transactionService.fundTransfer(request.getAmount(), accountOrigin, accountTarget);
		}

		return ResponseEntity.ok("Transferencia hecha");
	}

	@GetMapping("/transactions")
	public ResponseEntity<?> transactions() {

		User user = getAuthentication();

		Optional<Account> accountOrigin = accountService.findByUserId(user.getId());
		return ResponseEntity.ok(transactionService.findAll(accountOrigin.get()));
		
	}
	
	private User getAuthentication() {
		String username = authService.getAuth().getName();
		return userService.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
	}
}

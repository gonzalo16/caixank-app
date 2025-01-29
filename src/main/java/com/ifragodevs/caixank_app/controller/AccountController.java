package com.ifragodevs.caixank_app.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifragodevs.caixank_app.dto.AccountCreateDTO;
import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.entity.Transaction;
import com.ifragodevs.caixank_app.entity.TransactionStatus;
import com.ifragodevs.caixank_app.entity.User;
import com.ifragodevs.caixank_app.service.AccountService;
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
		
	//Debemos crear una cuenta nueva para el usuario. El account number de la cuenta nueva sera el de la cuenta principal
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody AccountCreateDTO request){
         
		
		// Obtiene el usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = authentication.getName();
		Optional<User> user = userService.findByUsername(username);
		
		
        Optional<Account> account = accountService.findByAccountNumber(UUID.fromString(request.getAccountNumber()));
        Account newAccount = Account.builder()
        		.accountNumber(account.get().getAccountNumber())
        		.accountType(request.getAccountType())
        		.balance(0.0)
        		.user(user.get())
        		.build();
        accountService.save(newAccount);
        
        return ResponseEntity.ok("New account added successfully for user");
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestBody Double request){
		// Obtiene el usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = authentication.getName();
		Optional<User> user = userService.findByUsername(username);
		
		Optional<Account> account = accountService.findMainAcountByUserId(user.get().getId());
		
		Double balance = account.get().getBalance();
		
		Double balanceFee = transactionService.applyFee(request);
		account.get().setBalance(balanceFee+balance);
		
		Transaction newTransaction = Transaction.builder()
				.amount(balanceFee)
				.account(account.get())
				.transactionStatus(TransactionStatus.PENDING)
				.build();
		account.get().addTransaction(newTransaction);
		
		transactionService.save(newTransaction);
		return ResponseEntity.ok("Cash deposited successfully " +request);
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<?> withdraw(@RequestBody Double amount){
		// Obtiene el usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = authentication.getName();
		Optional<User> user = userService.findByUsername(username);
		
		transactionService.retirar(amount, user.get().getId());
		return ResponseEntity.ok("Cash withdrawn successfully");
	}
}

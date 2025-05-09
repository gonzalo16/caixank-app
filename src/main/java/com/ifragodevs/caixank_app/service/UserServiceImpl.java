package com.ifragodevs.caixank_app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifragodevs.caixank_app.dto.MainAccountResponse;
import com.ifragodevs.caixank_app.dto.UserResponse;
import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.entity.User;
import com.ifragodevs.caixank_app.repository.AccountRepository;
import com.ifragodevs.caixank_app.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public UserResponse getUserInfo(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		Optional<Account> mainAccount = accountRepository.findMainAcountByUserId(user.get().getId());
		return UserResponse.builder()
				.username(username)
				.email(user.get().getEmail())
				.accountType(mainAccount.get().getAccountType())
				.accountNumber(mainAccount.get().getAccountNumber().toString())
				.hashedPassword(user.get().getPassword())
				.build();
	}

	@Override
	public MainAccountResponse getAccountMainInfo(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		
		Optional<Account> mainAccount = accountRepository.findMainAcountByUserId(user.get().getId());
		
		return MainAccountResponse.builder()
				.accountNumber(mainAccount.get().getAccountNumber().toString())
				.balance(mainAccount.get().getBalance())
				.accountType(mainAccount.get().getAccountType())
				.build();
	}

	@Override
	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}
}

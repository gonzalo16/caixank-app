package com.ifragodevs.caixank_app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.entity.User;
import com.ifragodevs.caixank_app.repository.AccountRepository;


@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Optional<Account> findByAccountType(String accountType) {
		return accountRepository.findByAccountType(accountType);
	}

	@Override
	public Optional<Account> findMainAcountByUserId(UUID userid) {
		return accountRepository.findMainAcountByUserId(userid);
	}

	@Override
	public Account save(Account newAccount) {
		return accountRepository.save(newAccount);
	}

	@Override
	public Optional<Account> findByAccountNumber(UUID accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Account> findById(Integer id) {
		return accountRepository.findById(id);
	}

	@Override
	public Optional<Account> findByUserId(UUID userid) {
		Optional<User> user = userService.findById(userid);
		return accountRepository.findByUserId(user.get().getId());
	}

	
}

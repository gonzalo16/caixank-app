package com.ifragodevs.caixank_app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.ifragodevs.caixank_app.entity.Account;

public interface AccountService{

	Optional<Account> findById(Integer id);
	
	Optional<Account> findByAccountType(String accountType);
	
	@Query("SELECT a FROM Account a WHERE a.user.id = :userid AND a.accountType = 'Main'")
	Optional<Account> findMainAcountByUserId(UUID userid);
	
	@Query("SELECT a FROM Account a WHERE a.user.id = :userid")
	Optional<Account> findByUserId(UUID userid);
	
	@Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
	Optional<Account> findByAccountNumber(UUID accountNumber);
	
	Account save(Account newAccount);
}

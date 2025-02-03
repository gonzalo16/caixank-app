package com.ifragodevs.caixank_app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ifragodevs.caixank_app.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	Optional<Account> findById(Integer id);

	Optional<Account> findByAccountType(String accountType);
	
	@Query("SELECT a FROM Account a WHERE a.user.id = :userid AND a.accountType = 'Main'")
	Optional<Account> findMainAcountByUserId(UUID userid);
	
	@Query("SELECT a FROM Account a WHERE a.user.id = :userid")
	Optional<Account> findByUserId(UUID userid);
	
	@Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
	Optional<Account> findByAccountNumber(UUID accountNumber);
	
}

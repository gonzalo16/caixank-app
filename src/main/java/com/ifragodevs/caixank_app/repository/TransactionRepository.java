package com.ifragodevs.caixank_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	@Query("SELECT t FROM Transaction t WHERE t.accountOrigin = :accountOrigin")
	List<Transaction> findAllByAccountOrigin(Account accountOrigin);
}

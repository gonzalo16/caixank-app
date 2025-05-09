package com.ifragodevs.caixank_app.service;

import java.util.List;
import java.util.UUID;

import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.entity.Transaction;

public interface TransactionService {

	Transaction save(Account account,Double mount);
	
	void retirar(Double mount,UUID userId);
	
	void fundTransfer(Double mount,Account accountOrigin, Account accountNumberTarget);
	
	Double applyFee(Double mount);
	
	List<Transaction> findAll(Account account);
}

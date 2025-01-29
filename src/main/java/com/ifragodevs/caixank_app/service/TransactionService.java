package com.ifragodevs.caixank_app.service;

import java.util.UUID;

import com.ifragodevs.caixank_app.entity.Transaction;

public interface TransactionService {

	Transaction save(Transaction t);
	
	void retirar(Double mount,UUID userId);
	
	Double applyFee(Double mount);
}

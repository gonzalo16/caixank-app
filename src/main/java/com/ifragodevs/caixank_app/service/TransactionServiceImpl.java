package com.ifragodevs.caixank_app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.entity.Transaction;
import com.ifragodevs.caixank_app.entity.TransactionStatus;
import com.ifragodevs.caixank_app.entity.User;
import com.ifragodevs.caixank_app.repository.TransactionRepository;


@Service
public class TransactionServiceImpl implements TransactionService{
	
	private final double FEEPERCENTAGE = 0.02; 

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
		
	@Override
	public Transaction save(Transaction t) {

		return transactionRepository.save(t);
	}
	
	@Override
	public Double applyFee(Double mount) {

		double finalMount = (mount > 50000) ? mount * (1 - FEEPERCENTAGE) : mount;
			
		return finalMount;
	}

	@Override
	@Transactional
	public void retirar(Double mount, UUID userId) {
		Optional<User> user = userService.findById(userId);
		Optional<Account> accountUser = accountService.findMainAcountByUserId(user.get().getId());
		
		
		if(mount > accountUser.get().getBalance()) {
			//Retonar error porque no se puede retirar una cantidad que no tiene en la cuenta
		}else {
			double balanceUser = accountUser.get().getBalance();
			balanceUser -= mount;
			accountUser.get().setBalance(balanceUser);
			
			//Guardamos en la base de datos la transaccion
			Transaction newTransaction = Transaction.builder()
					.amount(mount)
					.account(accountUser.get())
					.transactionStatus(TransactionStatus.PENDING)
					.build();
			accountUser.get().addTransaction(newTransaction);
			transactionRepository.save(newTransaction);
		}
		
	}
}

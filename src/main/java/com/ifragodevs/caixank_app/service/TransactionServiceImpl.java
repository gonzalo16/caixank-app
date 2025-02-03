package com.ifragodevs.caixank_app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.entity.Transaction;
import com.ifragodevs.caixank_app.entity.TransactionStatus;
import com.ifragodevs.caixank_app.entity.TransactionType;
import com.ifragodevs.caixank_app.entity.User;
import com.ifragodevs.caixank_app.exceptions.InvalidTransactionException;
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
	public Transaction save(Account account,Double mount) {
		if(mount == null || mount < 0) {
			throw new InvalidTransactionException("El monto debe ser mayor que cero");
		}
		Double balance = account.getBalance();
		Double balanceFee = applyFee(mount);
		account.setBalance(balanceFee + balance);
		
		Transaction newTransaction = Transaction.builder()
				.amount(balanceFee)
				.accountOrigin(account)
				.transactionType(TransactionType.CASH_DEPOSIT)
				.transactionStatus(TransactionStatus.PENDING)
				.build();
		account.addTransaction(newTransaction);
		
		return transactionRepository.save(newTransaction);
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
					.accountOrigin(accountUser.get())
					.transactionStatus(TransactionStatus.PENDING)
					.build();
			accountUser.get().addTransaction(newTransaction);
			transactionRepository.save(newTransaction);
		}
		
	}

	@Override
	@Transactional
	public void fundTransfer(Double mount,Account accountOrigin, Account accountNumberTarget) {
		if(mount == null || mount < 0) {
			throw new InvalidTransactionException("El monto debe ser mayor que cero");
		}
		
		Transaction newTransaction = null;
		newTransaction = Transaction.builder()
				.accountDestiny(accountNumberTarget)
				.accountOrigin(accountOrigin)
				.transactionType(TransactionType.CASH_TRANSFER)
				.amount(mount)
				.build();
		
		accountOrigin.setBalance(accountOrigin.getBalance() - mount);
		
		
		accountNumberTarget.setBalance(accountNumberTarget.getBalance() + mount);
		//update
		
		
		
		if(mount > 80000) {				
			newTransaction.setTransactionStatus(TransactionStatus.FRAUD);		
		}else {
			newTransaction.setTransactionStatus(TransactionStatus.APPROVED);
		}
		
		
		//Podemos hacer un update
		accountService.save(accountOrigin);
		accountService.save(accountNumberTarget);
		transactionRepository.save(newTransaction);
	}
}

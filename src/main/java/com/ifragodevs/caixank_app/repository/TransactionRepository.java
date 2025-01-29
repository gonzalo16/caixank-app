package com.ifragodevs.caixank_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifragodevs.caixank_app.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

}

package com.ifragodevs.caixank_app.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	
	private UUID accountNumber;
	
	private String accountType;
	
	private Double balance;
	
	@OneToMany(mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonIgnore
	@Builder.Default
	private List<Transaction> transactions = new ArrayList<>();
	
	public void addTransaction(Transaction t) {
		this.transactions.add(t);
	}
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user_id")
	private User user;
}

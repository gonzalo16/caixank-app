package com.ifragodevs.caixank_app.listeners;

import com.ifragodevs.caixank_app.entity.Account;

import jakarta.persistence.PostPersist;

public class AccountListener {

	@PostPersist
	public void onPostPersist(Account account) {
		if(account.getAccountType().equals("Invest")) {
			System.out.println("Se ha creado una cuenta de inversiones");
		}
	}
}

package com.ifragodevs.caixank_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainAccountResponse {

	String accountNumber;
	
	Double balance;
	
	String accountType;
}

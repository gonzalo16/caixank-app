package com.ifragodevs.caixank_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private String username;
	
	private String email;
	
	private String accountType;
	
	private String accountNumber;
	
	private String hashedPassword;
}

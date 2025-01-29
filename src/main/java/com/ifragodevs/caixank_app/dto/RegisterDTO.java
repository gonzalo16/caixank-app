package com.ifragodevs.caixank_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
    @Email
	private String email;
}

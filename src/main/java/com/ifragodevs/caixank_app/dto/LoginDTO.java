package com.ifragodevs.caixank_app.dto;

import com.ifragodevs.caixank_app.validations.IsExistUsername;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	@NotBlank
	@IsExistUsername
	String username;
	
	@NotBlank
    String password; 
}

package com.ifragodevs.caixank_app.service;

import java.util.Optional;
import java.util.UUID;

import com.ifragodevs.caixank_app.dto.MainAccountResponse;
import com.ifragodevs.caixank_app.dto.UserResponse;
import com.ifragodevs.caixank_app.entity.User;

public interface UserService {

	//Metodo personalizado de consulta
	boolean existsByUsername(String username);
	
	Optional<User> findById(UUID id);
	
	Optional<User> findByUsername(String username);
	
	UserResponse getUserInfo(String username);
	
	MainAccountResponse getAccountMainInfo(String username);
}

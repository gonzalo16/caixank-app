package com.ifragodevs.caixank_app.service;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifragodevs.caixank_app.auth.AuthResponse;
import com.ifragodevs.caixank_app.dto.LoginDTO;
import com.ifragodevs.caixank_app.dto.RegisterDTO;
import com.ifragodevs.caixank_app.dto.UserResponse;
import com.ifragodevs.caixank_app.entity.Account;
import com.ifragodevs.caixank_app.entity.Role;
import com.ifragodevs.caixank_app.entity.User;
import com.ifragodevs.caixank_app.jwt.JwtService;
import com.ifragodevs.caixank_app.repository.AccountRepository;
import com.ifragodevs.caixank_app.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final UserRepository userRepository;
	
	private final AuthenticationManager authenticationManager;
	
	private final AccountRepository accountRepository;
	
	@Transactional
	public UserResponse register(RegisterDTO registerDTO) {
		User user = User.builder()
	            .username(registerDTO.getUsername())
	            .password(passwordEncoder.encode( registerDTO.getPassword()))
	            .email(registerDTO.getEmail())
	            .role(Role.USER)
	            .build();
		
		Account account = Account.builder()
				.accountNumber(UUID.randomUUID())
				.accountType("Main")
				.balance(0.0)
				.user(user)
				.build();
		
		user.addCount(account);
		
		accountRepository.save(account);
		userRepository.save(user);
		
		return UserResponse.builder()
				.username(registerDTO.getUsername())
				.email(registerDTO.getEmail())
				.hashedPassword(registerDTO.getPassword())
				.accountNumber(account.getAccountNumber().toString())
				.accountType("Main")
				.build();
	}
	
	public AuthResponse login(LoginDTO request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
	}
}

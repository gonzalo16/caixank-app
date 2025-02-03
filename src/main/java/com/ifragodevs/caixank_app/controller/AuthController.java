package com.ifragodevs.caixank_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifragodevs.caixank_app.dto.LoginDTO;
import com.ifragodevs.caixank_app.dto.RegisterDTO;
import com.ifragodevs.caixank_app.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;

	@PostMapping(value = "register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO request, BindingResult result)
    {
		if(result.hasFieldErrors()) {
			return validation(result);
		}
		
        return ResponseEntity.ok(authService.register(request));
    }
	
	@PostMapping(value = "login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO request, BindingResult result)
    {
		if(result.hasFieldErrors()) {
			return validation(result);
		}
		
        return ResponseEntity.ok(authService.login(request));
    }
	
	private ResponseEntity<?> validation(BindingResult result) {
		Map<String,String> errors = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errors.put(err.getField(),err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errors);
	}
}

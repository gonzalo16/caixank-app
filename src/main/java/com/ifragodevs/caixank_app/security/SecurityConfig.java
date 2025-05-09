package com.ifragodevs.caixank_app.security;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authProvider;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(
						authRequest -> authRequest.requestMatchers("/auth/**").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(
						sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();

	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configCors = new CorsConfiguration();
		configCors.setAllowedOriginPatterns(Arrays.asList("*"));
		configCors.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT"));
		configCors.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
		configCors.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configCors);
		return source;
	}
	
	@Bean
	FilterRegistrationBean<CorsFilter> corsFilters(){
		FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return corsBean;
	}
}

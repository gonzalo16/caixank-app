package com.ifragodevs.caixank_app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifragodevs.caixank_app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,UUID>{

	 Optional<User> findByUsername(String username); 
	 
	 Optional<User> findById(UUID id);
	 
	 boolean existsByUsername(String username);
}

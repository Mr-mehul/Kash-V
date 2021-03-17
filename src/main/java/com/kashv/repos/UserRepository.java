package com.kashv.repos;

import com.kashv.domain.User;
import com.kashv.model.UserType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByUserType(UserType userType);

	Optional<User> findByEmail(String email);
	
	Optional<User> findByPhoneNumber(Long phoneNumber);
	

}

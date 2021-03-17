package com.kashv.repos;

import com.kashv.domain.PasswordResetToken;
import com.kashv.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);

	Long deleteByUser(User user);
}

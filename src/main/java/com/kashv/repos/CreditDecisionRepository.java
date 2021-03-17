package com.kashv.repos;

import com.kashv.domain.CreditDecision;
import com.kashv.domain.Profile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditDecisionRepository extends JpaRepository<CreditDecision, Long> {
	Optional<CreditDecision> findByCreditUserId_Id(Long userId);
}

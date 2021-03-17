package com.kashv.repos;

import com.kashv.domain.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {
}

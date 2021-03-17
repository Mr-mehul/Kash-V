package com.kashv.service;

import com.kashv.domain.CreditDecision;
import com.kashv.domain.LoanType;
import com.kashv.domain.Profile;
import com.kashv.domain.User;
import com.kashv.model.CreditDecisionDTO;
import com.kashv.model.ProfileDTO;
import com.kashv.repos.CreditDecisionRepository;
import com.kashv.repos.LoanTypeRepository;
import com.kashv.repos.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CreditDecisionService {

	private final CreditDecisionRepository creditDecisionRepository;
	private final LoanTypeRepository loanTypeRepository;
	private final UserRepository userRepository;

	public CreditDecisionService(final CreditDecisionRepository creditDecisionRepository,
			final LoanTypeRepository loanTypeRepository, final UserRepository userRepository) {
		this.creditDecisionRepository = creditDecisionRepository;
		this.loanTypeRepository = loanTypeRepository;
		this.userRepository = userRepository;
	}

	public List<CreditDecisionDTO> findAll() {
		return creditDecisionRepository.findAll().stream()
				.map(creditDecision -> mapToDTO(creditDecision, new CreditDecisionDTO())).collect(Collectors.toList());
	}

	public CreditDecisionDTO get(final Long creditId) {
		return creditDecisionRepository.findById(creditId)
				.map(creditDecision -> mapToDTO(creditDecision, new CreditDecisionDTO()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public CreditDecisionDTO create(CreditDecisionDTO creditDecisionDTO) {
		final CreditDecision creditDecision = new CreditDecision();
		mapToEntity(creditDecisionDTO, creditDecision);
		creditDecisionDTO.setCreditId(creditDecisionRepository.save(creditDecision).getCreditId());
		return creditDecisionDTO;
	}

	public void update(final Long creditId, final CreditDecisionDTO creditDecisionDTO) {
		final CreditDecision creditDecision = creditDecisionRepository.findById(creditId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		mapToEntity(creditDecisionDTO, creditDecision);
		creditDecisionRepository.save(creditDecision);
	}

	public void delete(final Long creditId) {
		creditDecisionRepository.deleteById(creditId);
	}

	private CreditDecisionDTO mapToDTO(final CreditDecision creditDecision, final CreditDecisionDTO creditDecisionDTO) {
		creditDecisionDTO.setCreditId(creditDecision.getCreditId());
		creditDecisionDTO.setJobType(creditDecision.getJobType());
		creditDecisionDTO.setSalary(creditDecision.getSalary());
		creditDecisionDTO.setBankName(creditDecision.getBankName());
		creditDecisionDTO.setBankAddress(creditDecision.getBankAddress());
		creditDecisionDTO.setQuestionAnswer(creditDecision.getQuestionAnswer());
		creditDecisionDTO
				.setTypeId(creditDecision.getTypeId() == null ? null : creditDecision.getTypeId().getLoanTypeId());
		creditDecisionDTO.setCreditUserId(
				creditDecision.getCreditUserId() == null ? null : creditDecision.getCreditUserId().getId());
		return creditDecisionDTO;
	}

	private CreditDecision mapToEntity(final CreditDecisionDTO creditDecisionDTO, final CreditDecision creditDecision) {
		creditDecision.setJobType(creditDecisionDTO.getJobType());
		creditDecision.setSalary(creditDecisionDTO.getSalary());
		creditDecision.setBankName(creditDecisionDTO.getBankName());
		creditDecision.setBankAddress(creditDecisionDTO.getBankAddress());
		creditDecision.setQuestionAnswer(creditDecisionDTO.getQuestionAnswer());
		if (creditDecisionDTO.getTypeId() != null && (creditDecision.getTypeId() == null
				|| !creditDecision.getTypeId().getLoanTypeId().equals(creditDecisionDTO.getTypeId()))) {
			final LoanType typeId = loanTypeRepository.findById(creditDecisionDTO.getTypeId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "typeId not found"));
			creditDecision.setTypeId(typeId);
		}
		if (creditDecisionDTO.getCreditUserId() != null && (creditDecision.getCreditUserId() == null
				|| !creditDecision.getCreditUserId().getId().equals(creditDecisionDTO.getCreditUserId()))) {
			final User creditUserId = userRepository.findById(creditDecisionDTO.getCreditUserId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "creditUserId not found"));
			creditDecision.setCreditUserId(creditUserId);
		}
		return creditDecision;
	}

	// Mehul Code

	public boolean existByUserId(final Long userId) {
		Optional<CreditDecision> data = creditDecisionRepository.findByCreditUserId_Id(userId);
		return data.isEmpty();
	}
	
	// Get Credit Decision details by userId
	public CreditDecisionDTO getByUserId(final Long id) {
		return creditDecisionRepository.findByCreditUserId_Id(id)
				.map(creditDecision -> mapToDTO(creditDecision, new CreditDecisionDTO()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

}

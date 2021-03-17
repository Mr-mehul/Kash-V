package com.kashv.service;

import com.kashv.domain.LoanType;
import com.kashv.model.LoanTypeDTO;
import com.kashv.repos.LoanTypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class LoanTypeService {

    private final LoanTypeRepository loanTypeRepository;

    public LoanTypeService(final LoanTypeRepository loanTypeRepository) {
        this.loanTypeRepository = loanTypeRepository;
    }

    public List<LoanTypeDTO> findAll() {
        return loanTypeRepository.findAll()
                .stream()
                .map(loanType -> mapToDTO(loanType, new LoanTypeDTO()))
                .collect(Collectors.toList());
    }

    public LoanTypeDTO get(final Long loanTypeId) {
        return loanTypeRepository.findById(loanTypeId)
                .map(loanType -> mapToDTO(loanType, new LoanTypeDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final LoanTypeDTO loanTypeDTO) {
        final LoanType loanType = new LoanType();
        mapToEntity(loanTypeDTO, loanType);
        return loanTypeRepository.save(loanType).getLoanTypeId();
    }

    public void update(final Long loanTypeId, final LoanTypeDTO loanTypeDTO) {
        final LoanType loanType = loanTypeRepository.findById(loanTypeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(loanTypeDTO, loanType);
        loanTypeRepository.save(loanType);
    }

    public void delete(final Long loanTypeId) {
        loanTypeRepository.deleteById(loanTypeId);
    }

    private LoanTypeDTO mapToDTO(final LoanType loanType, final LoanTypeDTO loanTypeDTO) {
        loanTypeDTO.setLoanTypeId(loanType.getLoanTypeId());
        loanTypeDTO.setTypeName(loanType.getTypeName());
        return loanTypeDTO;
    }

    private LoanType mapToEntity(final LoanTypeDTO loanTypeDTO, final LoanType loanType) {
        loanType.setTypeName(loanTypeDTO.getTypeName());
        return loanType;
    }

}
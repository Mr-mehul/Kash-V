package com.kashv.rest;

import com.kashv.config.Response;
import com.kashv.config.SessionHandler;
import com.kashv.constance.StringConstance;
import com.kashv.model.LoanTypeDTO;
import com.kashv.service.LoanTypeService;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/loanTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanTypeController {

	private final LoanTypeService loanTypeService;
	Response response;

	public LoanTypeController(final LoanTypeService loanTypeService) {
		this.loanTypeService = loanTypeService;
	}

	@GetMapping
	public ResponseEntity<Response> getAllLoanTypes(HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(loanTypeService.findAll());

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

//	@GetMapping("/{loanTypeId}")
//	public ResponseEntity<LoanTypeDTO> getLoanType(@PathVariable final Long loanTypeId) {
//		return ResponseEntity.ok(loanTypeService.get(loanTypeId));
//	}
//
	@PostMapping
	public ResponseEntity<Long> createLoanType(@RequestBody @Valid final LoanTypeDTO loanTypeDTO) {
		return new ResponseEntity<>(loanTypeService.create(loanTypeDTO), HttpStatus.CREATED);
	}
//
//	@PutMapping("/{loanTypeId}")
//	public ResponseEntity<Void> updateLoanType(@PathVariable final Long loanTypeId,
//			@RequestBody @Valid final LoanTypeDTO loanTypeDTO) {
//		loanTypeService.update(loanTypeId, loanTypeDTO);
//		return ResponseEntity.ok().build();
//	}
//
//	@DeleteMapping("/{loanTypeId}")
//	public ResponseEntity<Void> deleteLoanType(@PathVariable final Long loanTypeId) {
//		loanTypeService.delete(loanTypeId);
//		return ResponseEntity.noContent().build();
//	}

}
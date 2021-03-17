package com.kashv.rest;

import com.kashv.config.Response;
import com.kashv.config.SessionHandler;
import com.kashv.constance.StringConstance;
import com.kashv.model.CreditDecisionDTO;
import com.kashv.model.Request;
import com.kashv.service.CreditDecisionService;
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
@RequestMapping(value = "/api/creditDecisions", produces = MediaType.APPLICATION_JSON_VALUE)
public class CreditDecisionController {

	private final CreditDecisionService creditDecisionService;

	Response response;

	public CreditDecisionController(final CreditDecisionService creditDecisionService) {
		this.creditDecisionService = creditDecisionService;
	}

//	@GetMapping
//	public ResponseEntity<List<CreditDecisionDTO>> getAllCreditDecisions() {
//		return ResponseEntity.ok(creditDecisionService.findAll());
//	}

	// Get CreditDecision Details By User Id
	@GetMapping("user")
	public ResponseEntity<Response> getCreditDecision(@RequestBody Request requestDTO, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(creditDecisionService.getByUserId(requestDTO.getId()));

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// Create Credit Decision API
	@PostMapping
	public ResponseEntity<Response> createCreditDecision(@RequestBody @Valid CreditDecisionDTO creditDecisionDTO,
			HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			if (creditDecisionService.existByUserId(creditDecisionDTO.getCreditUserId()) == true) {
				response.setMessage(StringConstance.SUCCESSFUL);
				response.setData(creditDecisionService.create(creditDecisionDTO));
			} else {
				response.setMessage(StringConstance.ONLY_ONE_RECORD_ALLOW);
				return ResponseEntity.status(HttpStatus.MULTIPLE_CHOICES).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// Update CreditDecision API
	@PutMapping()
	public ResponseEntity<Response> updateCreditDecision(@RequestBody @Valid final CreditDecisionDTO creditDecisionDTO,
			HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			creditDecisionService.update(creditDecisionDTO.getCreditId(), creditDecisionDTO);
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(creditDecisionService.get(creditDecisionDTO.getCreditId()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

//	@DeleteMapping("/{creditId}")
//	public ResponseEntity<Void> deleteCreditDecision(@PathVariable final Long creditId) {
//		creditDecisionService.delete(creditId);
//		return ResponseEntity.noContent().build();
//	}

}

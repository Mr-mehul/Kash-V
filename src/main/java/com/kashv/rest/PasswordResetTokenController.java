package com.kashv.rest;

import com.kashv.config.Response;
import com.kashv.constance.StringConstance;
import com.kashv.domain.PasswordResetToken;
import com.kashv.domain.User;
import com.kashv.model.PasswordResetDto;
import com.kashv.model.PasswordResetTokenDTO;
import com.kashv.repos.PasswordResetTokenRepository;
import com.kashv.service.PasswordResetTokenService;
import com.kashv.service.UserService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/api/passwordResetTokens", produces = MediaType.APPLICATION_JSON_VALUE)
public class PasswordResetTokenController {

	private final PasswordResetTokenService passwordResetTokenService;
	private final PasswordResetTokenRepository tokenRepository;
	private final UserService userService;
	Response response;

	public PasswordResetTokenController(final PasswordResetTokenService passwordResetTokenService,
			final PasswordResetTokenRepository tokenRepository, final UserService userService) {
		this.passwordResetTokenService = passwordResetTokenService;
		this.tokenRepository = tokenRepository;
		this.userService = userService;
	}

	// forgot password method for sending email (Admin Portal)
	@PostMapping("forgotPassword")
	public boolean forgotPassword(HttpSession session, @RequestParam("email") final String email,
			HttpServletRequest request) {
		try {
			passwordResetTokenService.sendResetLinkToMailByEmail(session, email, request);
			// mv.setViewName(StringConstance.ADMIN_LOGIN);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// forgot password API for sending email link (Android)
	@PostMapping("forgotPasswordAPI")
	public ResponseEntity<Response> forgotPasswordAPI(HttpSession session, @RequestParam("email") final String email,
			HttpServletRequest request) {
		response = new Response();
		try {
			response.setMessage(passwordResetTokenService.sendResetLinkToMailByEmail(session, email, request));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}

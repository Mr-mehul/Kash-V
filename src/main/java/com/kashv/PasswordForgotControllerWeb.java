package com.kashv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kashv.constance.StringConstance;
import com.kashv.domain.PasswordResetToken;
import com.kashv.domain.User;
import com.kashv.model.PasswordResetDto;
import com.kashv.model.PasswordResetTokenDTO;
import com.kashv.repos.PasswordResetTokenRepository;
import com.kashv.service.PasswordResetTokenService;
import com.kashv.service.UserService;

@Controller
public class PasswordForgotControllerWeb {

	ModelAndView mv;

	private final PasswordResetTokenService passwordResetTokenService;
	private final PasswordResetTokenRepository tokenRepository;
	private final UserService userService;

	public PasswordForgotControllerWeb(final PasswordResetTokenService passwordResetTokenService,
			final PasswordResetTokenRepository tokenRepository, final UserService userService) {
		this.passwordResetTokenService = passwordResetTokenService;
		this.tokenRepository = tokenRepository;
		this.userService = userService;
	}

	@RequestMapping("reset-password")
	public ModelAndView displayResetPasswordPage(@RequestParam(required = false) String token) {
		mv = new ModelAndView();
		PasswordResetToken resetToken = tokenRepository.findByToken(token);
		if (resetToken == null) {
			mv.setViewName(StringConstance.NOT_FOUND_PAGE);
			mv.addObject("message",  StringConstance.TOKEN_NOT_FOUND);
		} else if (resetToken.isExpired()) {
			mv.setViewName(StringConstance.NOT_FOUND_PAGE);
			mv.addObject("message", StringConstance.TOKEN_EXPIRED);
		} else {
			mv.setViewName(StringConstance.RESET_PASSWORD_PAGE);
			mv.addObject("token", resetToken.getToken());
		}

		return mv;
	}

	@PostMapping("reset-password")
	@Transactional
	public ModelAndView handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form) {
		mv = new ModelAndView();
		PasswordResetToken token = tokenRepository.findByToken(form.getToken());
		if (token != null) {
			User user = token.getUser();
			boolean status = userService.updatePassword(form.getPassword(), user.getId());
			if (status == true) {
				tokenRepository.delete(token);
				mv.setViewName(StringConstance.ADMIN_LOGIN);
				mv.addObject("message", StringConstance.PASSWORD_RESET_SUCCESSFUL);
				tokenRepository.delete(token);
			} else {
				mv.setViewName(StringConstance.ERROR_PAGE);
				mv.addObject("message", StringConstance.UNABLE_TO_RESET_PASSWORD);
			}
		} else if (token.isExpired()) {
			mv.setViewName(StringConstance.NOT_FOUND_PAGE);
			mv.addObject("message", StringConstance.TOKEN_EXPIRED);
		} else {
			mv.setViewName(StringConstance.NOT_FOUND_PAGE);
			mv.addObject("message", StringConstance.TOKEN_NOT_FOUND);
		}
		return mv;
	}
}

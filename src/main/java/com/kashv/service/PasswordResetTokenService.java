package com.kashv.service;

import com.kashv.constance.StringConstance;
import com.kashv.domain.PasswordResetToken;
import com.kashv.domain.User;
import com.kashv.model.PasswordResetTokenDTO;
import com.kashv.repos.PasswordResetTokenRepository;
import com.kashv.repos.UserRepository;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;

@Service
public class PasswordResetTokenService {

	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final UserRepository userRepository;
	private final JavaMailSender mailSender;
	private final SimpleMailMessage preConfiguredMessage;

	public PasswordResetTokenService(final PasswordResetTokenRepository passwordResetTokenRepository,
			final UserRepository userRepository, final JavaMailSender mailSender,
			final SimpleMailMessage preConfiguredMessage) {
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.userRepository = userRepository;
		this.mailSender = mailSender;
		this.preConfiguredMessage = preConfiguredMessage;
	}

	public List<PasswordResetTokenDTO> findAll() {
		return passwordResetTokenRepository.findAll().stream()
				.map(passwordResetToken -> mapToDTO(passwordResetToken, new PasswordResetTokenDTO()))
				.collect(Collectors.toList());
	}

	public PasswordResetTokenDTO get(final Long id) {
		return passwordResetTokenRepository.findById(id)
				.map(passwordResetToken -> mapToDTO(passwordResetToken, new PasswordResetTokenDTO()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Long create(final PasswordResetTokenDTO passwordResetTokenDTO) {
		final PasswordResetToken passwordResetToken = new PasswordResetToken();
		mapToEntity(passwordResetTokenDTO, passwordResetToken);
		return passwordResetTokenRepository.save(passwordResetToken).getId();
	}

	public void update(final Long id, final PasswordResetTokenDTO passwordResetTokenDTO) {
		final PasswordResetToken passwordResetToken = passwordResetTokenRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		mapToEntity(passwordResetTokenDTO, passwordResetToken);
		passwordResetTokenRepository.save(passwordResetToken);
	}

	public void delete(final Long id) {
		passwordResetTokenRepository.deleteById(id);
	}

	private PasswordResetTokenDTO mapToDTO(final PasswordResetToken passwordResetToken,
			final PasswordResetTokenDTO passwordResetTokenDTO) {
		passwordResetTokenDTO.setId(passwordResetToken.getId());
		passwordResetTokenDTO.setToken(passwordResetToken.getToken());
		passwordResetTokenDTO.setExpiryDate(passwordResetToken.getExpiryDate());
		passwordResetTokenDTO
				.setUser(passwordResetToken.getUser() == null ? null : passwordResetToken.getUser().getId());
		return passwordResetTokenDTO;
	}

	private PasswordResetToken mapToEntity(final PasswordResetTokenDTO passwordResetTokenDTO,
			final PasswordResetToken passwordResetToken) {
		passwordResetToken.setToken(passwordResetTokenDTO.getToken());
		passwordResetToken.setExpiryDate(passwordResetTokenDTO.getExpiryDate());
		if (passwordResetTokenDTO.getUser() != null && (passwordResetToken.getUser() == null
				|| !passwordResetToken.getUser().getId().equals(passwordResetTokenDTO.getUser()))) {
			final User user = userRepository.findById(passwordResetTokenDTO.getUser())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
			passwordResetToken.setUser(user);
		}
		return passwordResetToken;
	}

	// Mehul Code
	// send mail without attachment
	public boolean sendPreConfiguredMail(String message, String email) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
			// baseUrl = baseUrl + userData.getUserId() + "/" + userData.getUserPhoneNo();
			// String message = "Please click bellow link to reset your accuont password ! "
			// + baseUrl;
			mailMessage.setText(message);
			mailMessage.setTo(email);
			mailSender.send(mailMessage);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// send mail with attachment
	public void sendMailWithAttachment(String to, String subject, String body, String fileToAttach) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				mimeMessage.setFrom(new InternetAddress("admin@gmail.com"));
				mimeMessage.setSubject(subject);
				mimeMessage.setText(body);

				FileSystemResource file = new FileSystemResource(new File(fileToAttach));
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.addAttachment("logo.jpg", file);
			}
		};

		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}
	}

	// send Reset URL to mail with emai id
	public String sendResetLinkToMailByEmail(HttpSession session, final String email, HttpServletRequest request) {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user != null) {
			PasswordResetToken token = new PasswordResetToken();
			token.setToken(UUID.randomUUID().toString());
			token.setUser(user);
			token.setExpiryDate(5);
			passwordResetTokenRepository.save(token);
			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/reset-password?token=" + token.getToken();

			boolean status = sendPreConfiguredMail(url, user.getEmail());
			if (status == true) {
				return StringConstance.SENT;
			} else {
				return StringConstance.FAILED;
			}
		} else {
			return StringConstance.NOT_FOUND;
		}
	}

	// send Reset URL to mail with phone number
	public String sendResetLinkToMailByPhonenumber(HttpSession session, final Long phoneNumber,
			HttpServletRequest request) {
		User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
		if (user != null) {
			PasswordResetToken token = new PasswordResetToken();
			token.setToken(UUID.randomUUID().toString());
			token.setUser(user);
			token.setExpiryDate(5);
			passwordResetTokenRepository.save(token);
			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/reset-password?token=" + token.getToken();

			boolean status = sendPreConfiguredMail(url, user.getEmail());
			if (status == true) {
				return StringConstance.SENT;
			} else {
				return StringConstance.FAILED;
			}
		} else {
			return StringConstance.USER_NOT_REGISTERED;
		}
	}
}

package com.kashv.service;

import java.io.File;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.kashv.constance.StringConstance;
import com.kashv.domain.PasswordResetToken;
import com.kashv.domain.User;
import com.kashv.repos.PasswordResetTokenRepository;
import com.kashv.repos.UserRepository;

@Service
public class EmailService {

	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final UserRepository userRepository;
	private final JavaMailSender mailSender;
	private final SimpleMailMessage preConfiguredMessage;

	public EmailService(final PasswordResetTokenRepository passwordResetTokenRepository,
			final UserRepository userRepository, final JavaMailSender mailSender,
			final SimpleMailMessage preConfiguredMessage) {
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.userRepository = userRepository;
		this.mailSender = mailSender;
		this.preConfiguredMessage = preConfiguredMessage;
	}

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

	// send Reset URL to mail using email id user
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

	// send Reset URL to mail using id user
	public String sendResetLinkToMailById(HttpSession session, final Long id, HttpServletRequest request) {
		User user = userRepository.findById(id).orElse(null);
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

	// send OTPL to mail using id user
	public int sendOtpToMailById(HttpSession session, final Long id) {
		User user = userRepository.findById(id).orElse(null);
		if (user != null) {
			int otp = (int) (Math.random() * 7000 + 3000);
			String text = "Your otp is" + otp;
			boolean status = sendPreConfiguredMail(text, user.getEmail());
			if (status == true) {
				return otp;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// send OTPL to mail using id email id
	public int sendOtpToMailByEmail(HttpSession session, final String email) {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user != null) {
			int otp = (int) (Math.random() * 7000 + 3000);
			String text = "Your otp is" + otp;
			boolean status = sendPreConfiguredMail(text, user.getEmail());
			if (status == true) {
				return otp;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// send OTPL to mail using id email id
	public int sendOtpToMailByPhonenumber(HttpSession session, final Long phoneNumber) {
		User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
		if (user != null) {
			int otp = (int) (Math.random() * 7000 + 3000);
			String text = "Your otp is" + otp;
			boolean status = sendPreConfiguredMail(text, user.getEmail());
			if (status == true) {
				return otp;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
}

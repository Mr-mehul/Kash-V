package com.kashv.service;

import com.kashv.constance.StringConstance;
import com.kashv.domain.Profile;
import com.kashv.domain.User;
import com.kashv.model.UserDTO;
import com.kashv.model.UserType;
import com.kashv.repos.ProfileRepository;
import com.kashv.repos.UserRepository;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;

	public UserService(final UserRepository userRepository, final ProfileRepository profileRepository) {
		this.userRepository = userRepository;
		this.profileRepository = profileRepository;
	}

	public List<UserDTO> findAll() {
		return userRepository.findAll().stream().map(user -> mapToDTO(user, new UserDTO()))
				.collect(Collectors.toList());
	}

	public UserDTO get(final Long id) {
		return userRepository.findById(id).map(user -> mapToDTO(user, new UserDTO()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public UserDTO create(UserDTO userDTO) {
		final User user = new User();
		mapToEntity(userDTO, user);
		userDTO.setId(userRepository.save(user).getId());
		return userDTO;
	}

	public void update(final Long id, UserDTO userDTO) {
		final User user = userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		mapToEntity(userDTO, user);
		userRepository.save(user);
	}

	public void delete(final Long id) {
		userRepository.deleteById(id);
	}

	private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setName(user.getName());
		userDTO.setReferenceId(user.getReferenceId());
		userDTO.setUserType(user.getUserType());
		userDTO.setPassword(user.getPassword());
		userDTO.setBlock(user.getBlock());
		return userDTO;
	}

	private User mapToEntity(final UserDTO userDTO, final User user) {
		user.setEmail(userDTO.getEmail());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setName(userDTO.getName());
		user.setReferenceId(userDTO.getReferenceId());
		user.setUserType(userDTO.getUserType());
		user.setPassword(userDTO.getPassword());
		user.setBlock(userDTO.getBlock());
		return user;
	}

	// Mehul Code

	// Login Service For FrontEnd - BackEnd
	public UserDTO userLogin(HttpSession session, final String email, String password) {
		User data = userRepository.findByEmail(email).orElse(null);
		if (data != null && data.getPassword().equals(password) == true && data.getBlock() == false
				&& (data.getUserType().equals(UserType.ADMIN) || data.getUserType().equals(UserType.SUPER_ADMIN))) {
			session.setAttribute("status", "yes");
			session.setAttribute("userId", data.getId());
			session.setAttribute("phoneNumber", data.getPhoneNumber());
			session.setAttribute("email", data.getEmail());
			session.setAttribute("userType", data.getUserType());
			session.setAttribute("type", data.getUserType().toString());
			return mapToDTO(data, new UserDTO());
		} else {
			return null;
		}
	}

	// Login Service For FrontEnd (Android)
	public UserDTO userLoginAPI(HttpSession session, final Long phoneNumber, UserType type) {
		User data = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
		if (data != null && data.getUserType().equals(type) == true) {
			session.setAttribute("status", "yes");
			session.setAttribute("userId", data.getId());
			session.setAttribute("phoneNumber", data.getPhoneNumber());
			session.setAttribute("email", data.getEmail());
			session.setAttribute("userType", data.getUserType());
			session.setAttribute("type", data.getUserType().toString());
			return mapToDTO(data, new UserDTO());
		} else {
			return null;
		}
	}

	// Check Password And Phone number(Android)
	public UserDTO checkPassword(final Long phoneNumber, final String password) {
		User data = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
		if (data != null && data.getPassword().equalsIgnoreCase(password)) {
			return mapToDTO(data, new UserDTO());
		} else {
			return null;
		}
	}

	// find All data by user type
	public List<UserDTO> findAllByUserType(UserType userType) {
		return userRepository.findByUserType(userType).stream().map(user -> mapToDTO(user, new UserDTO()))
				.collect(Collectors.toList());
	}

	// Update Password
	public Boolean updatePassword(final String password, final Long id) {
		User data = userRepository.findById(id).orElse(null);
		if (data != null) {
			data.setPassword(password);
			userRepository.save(data);
			return true;
		}
		return false;
	}

	// Blocking/Unblocking User
	public Boolean blockUser(final Long id, final Boolean block) {
		User data = userRepository.findById(id).orElse(null);
		if (data != null) {
			data.setBlock(block);
			userRepository.save(data);
			return true;
		}
		return false;
	}

	// validation user number and email is register with us or not
	public String validation(final Long phoneNumber, final String email) {
		if (userRepository.findByPhoneNumber(phoneNumber).orElse(null) != null) {
			return "Phone number is already register";
		} else if (userRepository.findByEmail(email).orElse(null) != null) {
			return "Email is prasent with us";
		} else {
			return null;
		}
	}

	// validation user number is register with us or not
	public String validation(final Long phoneNumber) {
		if (userRepository.findByPhoneNumber(phoneNumber).orElse(null) != null) {
			return "Phone number is already register";
		} else {
			return null;
		}
	}

	// Get User detail by PhoneNumber
	public UserDTO getByPhoneNumber(final Long phoneNumber) {
		return userRepository.findByPhoneNumber(phoneNumber).map(user -> mapToDTO(user, new UserDTO())).orElse(null);
	}
}

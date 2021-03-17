package com.kashv.rest;

import com.kashv.config.Response;
import com.kashv.config.SessionHandler;
import com.kashv.model.UserDTO;
import com.kashv.model.UserType;
import com.kashv.service.EmailService;
import com.kashv.service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kashv.constance.StringConstance;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private final UserService userService;
	private final EmailService emailService;

	Response response;

	public UserController(final UserService userService, final EmailService emailService) {
		this.userService = userService;
		this.emailService = emailService;
	}

//	@GetMapping
//	public ResponseEntity<List<UserDTO>> getAllUsers() {
//		return ResponseEntity.ok(userService.findAll());
//	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getUser(@PathVariable final Long id, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(userService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("signUp")
	public ResponseEntity<Response> createUser(@RequestBody @Valid UserDTO userDTO, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.getOtp(session) == userDTO.getOtp()
					&& SessionHandler.getPhoneNumber(session).equals(userDTO.getPhoneNumber())) {
				String validationStatus = userService.validation(userDTO.getPhoneNumber());
				if (validationStatus == null) {
					userService.create(userDTO);
					UserDTO data = userService.userLoginAPI(session, userDTO.getPhoneNumber(), UserType.USER);
					response.setMessage(StringConstance.SUCCESSFUL);
					response.setData(data);
					session.removeAttribute("otp");
				} else {
					response.setMessage(validationStatus);
					response.setData(null);
				}
			} else {
				response.setMessage(StringConstance.INVALID_OTP);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping()
	public ResponseEntity<Response> updateUser(@RequestBody @Valid UserDTO userDTO, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			userService.update(userDTO.getId(), userDTO);
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(userService.get(userDTO.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteUser(@PathVariable final Long id) {
//		userService.delete(id);
//		return ResponseEntity.noContent().build();
//	}

	// Mehul Code

	// Send OTP
	@PostMapping("sendOtp")
	public ResponseEntity<Response> sendOtp(@RequestParam("phoneNumber") final Long phoneNumber, HttpSession session) {
		response = new Response();
		try {
			UserDTO data = userService.getByPhoneNumber(phoneNumber);
			if (data != null) {
				int otp = (int) (Math.random() * 7000 + 3000);
				// int otp = emailService.sendOtpToMailByPhonenumber(session, phoneNumber);
				session.setAttribute("otp", otp);
				session.setAttribute("phoneNumber", phoneNumber);
				response.setMessage(StringConstance.SUCCESSFUL);
				response.setOtp(otp);

			} else {
				response.setMessage(StringConstance.USER_NOT_REGISTERED);
				int otp = (int) (Math.random() * 7000 + 3000);
				session.setAttribute("otp", otp);
				session.setAttribute("phoneNumber", phoneNumber);
				response.setOtp(otp);
			}
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// Login API For Android
	@GetMapping("/loginAPI")
	public ResponseEntity<Response> getUser(@RequestParam("phoneNumber") final Long phoneNumber,
			@RequestParam("otp") final int otp, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.getOtp(session) == otp && SessionHandler.getPhoneNumber(session).equals(phoneNumber)) {
				session.removeAttribute("otp");
				UserDTO data = userService.userLoginAPI(session, phoneNumber, UserType.USER);
				if (data != null) {
					response.setMessage(StringConstance.SUCCESSFUL);
					response.setData(data);
				} else {
					response.setMessage(StringConstance.USER_NOT_REGISTERED);
					return ResponseEntity.noContent().build();
				}
			} else {
				response.setMessage(StringConstance.INVALID_OTP);
			}
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// password verification(Android)
	@GetMapping("pinVerification")
	public ResponseEntity<Response> pinVerification(@RequestParam("phoneNumber") final Long phoneNumber,
			@RequestParam("password") final String password, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			UserDTO data = userService.checkPassword(phoneNumber, password);
			if (data != null) {
				response.setMessage(StringConstance.SUCCESSFUL);
				response.setData(data);
			} else {
				response.setMessage(StringConstance.FAILED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}

package com.kashv.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kashv.config.FileOperation;
import com.kashv.constance.StringConstance;
import com.kashv.model.Response;
import com.kashv.model.UserDTO;
import com.kashv.service.UserService;

@RestController
@RequestMapping(value = "/api/function", produces = MediaType.APPLICATION_JSON_VALUE)
public class FunctionalController {

	private String realPath;
	Response response;
	ModelAndView mv;

	private final UserService userService;
	
	public FunctionalController(final UserService userService) {
		this.userService = userService;
	}

	// login For Admin Portal
	@GetMapping("adminLogin")
	public boolean adminLoginByEmailAndPass(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpSession session) {
		UserDTO data = userService.userLogin(session, email, password);
		if (data != null) {
			return true;
		} else {
			return false;
		}

	}


	// saving image API
	@PostMapping("saveImageData")
	public ResponseEntity<Response> saveImage(@RequestParam("image") MultipartFile[] files, HttpSession session) {
		try {
			realPath = session.getServletContext().getRealPath(StringConstance.IMAGEFOLDER);
			response = new Response();
			String[] images = FileOperation.saveMultipalFiles(files, realPath);
			response.setMessage("Data saved successfully");
			response.setImages(images);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("logoutJavaScript")
	public String logoutJavaScript(HttpSession session) {
		session.setAttribute("status", "notallow");
		session.invalidate();
		return "true";
	}

	// logOut API
	@PostMapping("logoutAPI")
	public ResponseEntity<Response> logoutAPI(HttpSession session) {
		try {
			session.invalidate();
			response.setMessage("User successfully logout");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
}

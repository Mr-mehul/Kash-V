package com.kashv.rest;

import com.kashv.config.Response;
import com.kashv.config.SessionHandler;
import com.kashv.constance.StringConstance;
import com.kashv.model.ProfileDTO;
import com.kashv.model.Request;
import com.kashv.model.UserDTO;
import com.kashv.service.ProfileService;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

	private final ProfileService profileService;

	Response response;

	public ProfileController(final ProfileService profileService) {
		this.profileService = profileService;
	}

//    @GetMapping
//    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
//        return ResponseEntity.ok(profileService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ProfileDTO> getProfile(@PathVariable final Long id) {
//        return ResponseEntity.ok(profileService.get(id));
//    }

	// Get Profile Details By User Id
	@GetMapping("user")
	public ResponseEntity<Response> getProfileByUser(@RequestBody Request requestDTO, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(profileService.getByUserId(requestDTO.getId()));

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	//Create User Profile API
	@PostMapping
	public ResponseEntity<Response> createProfile(@RequestBody @Valid ProfileDTO profileDTO, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			if (profileService.existByUserId(profileDTO.getUserId()) == true) {
				response.setMessage(StringConstance.SUCCESSFUL);
				response.setData(profileService.create(profileDTO));
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

	// Update user profile API
	@PutMapping()
	public ResponseEntity<Response> updateProfile(@RequestBody @Valid ProfileDTO profileDTO, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			profileService.update(profileDTO.getId(), profileDTO);
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(profileService.get(profileDTO.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProfile(@PathVariable final Long id) {
//        profileService.delete(id);
//        return ResponseEntity.noContent().build();
//    }

}
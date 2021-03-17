package com.kashv.rest;

import com.kashv.config.Response;
import com.kashv.config.SessionHandler;
import com.kashv.constance.StringConstance;
import com.kashv.model.FileDataDTO;
import com.kashv.service.FileDataService;
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
@RequestMapping(value = "/api/fileDatas", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileDataController {

	private final FileDataService fileDataService;

	Response response;

	public FileDataController(final FileDataService fileDataService) {
		this.fileDataService = fileDataService;
	}

//    @GetMapping
//    public ResponseEntity<List<FileDataDTO>> getAllFileDatas() {
//        return ResponseEntity.ok(fileDataService.findAll());
//    }

	@GetMapping("/{id}")
	public ResponseEntity<Response> getFileData(@PathVariable final Long id, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(fileDataService.get(id));

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<Response> createFileData(@RequestBody @Valid FileDataDTO fileDataDTO, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(fileDataService.create(fileDataDTO));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	//Update User Profile Image
	@PutMapping()
	public ResponseEntity<Response> updateFileData(@RequestBody @Valid final FileDataDTO fileDataDTO,
			HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			response.setMessage(StringConstance.SUCCESSFUL);
			response.setData(fileDataService.update(fileDataDTO.getId(), fileDataDTO));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteFileData(@PathVariable final Long id, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			fileDataService.delete(id);
			response.setMessage(StringConstance.SUCCESSFUL);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
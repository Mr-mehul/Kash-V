package com.kashv.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kashv.config.FileOperation;
import com.kashv.config.Response;
import com.kashv.config.SessionHandler;
import com.kashv.constance.StringConstance;
import com.kashv.service.AmazonS3ClientService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/files")
public class FileHandlerController {

	@Autowired
	private AmazonS3ClientService amazonS3ClientService;

	Response response;

	@PostMapping
	public ResponseEntity<Response> uploadFile(@RequestPart(value = "file") MultipartFile file, HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			String fileName = file.getOriginalFilename();
			String[] fileNameSplits = fileName.split("\\.");
			String fileExtensionIndex = fileNameSplits[fileNameSplits.length - 1];
			fileName = FileOperation.getRandomString() + "." + fileExtensionIndex;
			String url = this.amazonS3ClientService.uploadFileToS3Bucket(file, true, fileName);
			if (url != null) {
				response.setMessage(StringConstance.SUCCESSFUL);
				response.setFileName(file.getOriginalFilename());
				response.setKey(fileName);
				response.setUrl(url);
			} else {
				response.setMessage(StringConstance.FAILED);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping
	public ResponseEntity<Response> deleteFile(@RequestParam("key") String key,HttpSession session) {
		response = new Response();
		try {
			if (SessionHandler.checkStatus(session) != true) {
				response.setMessage(StringConstance.NOT_LOGED_IN);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			boolean status = this.amazonS3ClientService.deleteFileFromS3Bucket(key);
			if (status == true) {
				response.setMessage(StringConstance.SUCCESSFUL);
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

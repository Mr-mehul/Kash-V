package com.kashv.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AmazonS3ClientService {

	private String awsS3AudioBucket;
	private AmazonS3 amazonS3;

	@Autowired
	public AmazonS3ClientService(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider,
			String awsS3AudioBucket) {
		this.amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider)
				.withRegion(awsRegion.getName()).build();
		this.awsS3AudioBucket = awsS3AudioBucket;
	}

	@Async
	public String uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess, String fileName) {

		try {
			// creating the file in the server (temporarily)
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
			fos.close();

			PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);

			if (enablePublicReadAccess) {
				putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
			}
			this.amazonS3.putObject(putObjectRequest);
			URL url = amazonS3.getUrl(this.awsS3AudioBucket, fileName);
			file.delete();
			return url.toString();

		} catch (IOException | AmazonServiceException ex) {
			System.out.println("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
			// logger.error("error [" + ex.getMessage() + "] occurred while uploading [" +
			// fileName + "] ");
			return null;
		}
	}

	@Async
	public boolean deleteFileFromS3Bucket(String key) {
		try {
			amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, key));
			return true;
		} catch (AmazonServiceException ex) {
			System.out.println("error [" + ex.getMessage() + "] occurred while removing [" + key + "] ");
			return false;
			// logger.error("error [" + ex.getMessage() + "] occurred while removing [" +
			// fileName + "] ");
		}
	}

}

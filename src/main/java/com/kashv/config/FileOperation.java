package com.kashv.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class FileOperation {

	// static Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
	// static Path filePath = Paths.get(root.toString(), "images");

	// One File save and returning file name
//	public static String saveFile(MultipartFile file) throws IOException {
//		String fileName = file.getOriginalFilename();
//		String[] fileNameSplits = fileName.split("\\.");
//		String fileExtensionIndex = fileNameSplits[fileNameSplits.length - 1];
//		fileName = getRandomString() + "." + fileExtensionIndex;
//		if (!file.isEmpty()) {
//			byte[] imageByteArray = file.getBytes();
//			Path path = Paths.get(realPath + "/" + fileName);
//			try {
//				Files.write(path, imageByteArray);
//			} catch (IOException e) {
//				e.printStackTrace();
//				return null;
//			}
//		} else {
//			return null;
//		}
//		return fileName;
//	}

	// Multiple file save and returning all files name
	public static String[] saveMultipalFiles(MultipartFile[] files, String realPath) throws IOException {
		String[] filesName = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String fileName = file.getOriginalFilename();
			String[] fileNameSplits = fileName.split("\\.");
			String fileExtensionIndex = fileNameSplits[fileNameSplits.length - 1];
			fileName = getRandomString() + "." + fileExtensionIndex;
			if (!file.isEmpty()) {
				byte[] imageByteArray = file.getBytes();
				Path path = Paths.get(realPath + "/" + fileName);
				try {
					Files.write(path, imageByteArray);
					filesName[i] = fileName;
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("file is not Stored : " + file.getOriginalFilename());
				}
			} else {
				System.out.println("file is empty : " + file.getOriginalFilename());
			}
		}
		return filesName;
	}

	// Delete file
	public static Boolean deleteFile(String Image, String realPath) {
		Path filePath = Paths.get(realPath, Image);
		File files = new File(filePath.toString());
		if (files.exists() == true) {
			files.delete();
			return true;
		} else
			System.out.println("File is not exists");
			return false;

	}

	// Delete multiple files
//	public static Boolean deleteMultipleFiles(String[] Images, String realPath) {
//		for (int i = 0; i < Images.length; i++) {
//			if (daoMasterData.existsMasterDataByProductImages(Images[i]) != true
//					&& daoProduct.existsProductsByProductImages(Images[i]) != true) {
//				Path filePath = Paths.get(realPath, Images[i]);
//				File files = new File(filePath.toString());
//				if (files.exists() == true) {
//					files.delete();
//					System.out.println("file is deleted : " + files.toString());
//				} else
//					System.out.println("file not deleted : " + files.toString());
//			}
//		}
//		return true;
//	}

	// String Generator
	public static String getRandomString() {
		int length = 15;
		final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890";
		StringBuilder result = new StringBuilder();
		while (length > 0) {
			Random rand = new Random();
			result.append(characters.charAt(rand.nextInt(characters.length())));
			length--;
		}
		return result.toString();
	}

	public static String saveFile(MultipartFile file, String realPath) throws IOException {
		String fileName = file.getOriginalFilename();
		String[] fileNameSplits = fileName.split("\\.");
		String fileExtensionIndex = fileNameSplits[fileNameSplits.length - 1];
		fileName = getRandomString() + "." + fileExtensionIndex;
		if (!file.isEmpty()) {
			byte[] imageByteArray = file.getBytes();
			Path path = Paths.get(realPath + "/" + fileName);
			try {
				Files.write(path, imageByteArray);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		return fileName;
	}
}

package com.sns.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {

	public static final String FILE_UPLOAD_PATH = "D:\\6_spring_project\\sns\\sns_workspace\\images/";
	// public static final String FILE_UPLOAD_PATH = "D:\\6_spring_project\\sns\\sns_workspace\\image/";
	
	// input: 업로드할 파일, 유저 아이디
	// output: 이미지 경로
	public String uploadFile(MultipartFile file, String loginId) {
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) { // 폴더 생성 실패 시 경로 null 리턴
			return null;
		}
		
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path,  bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
}

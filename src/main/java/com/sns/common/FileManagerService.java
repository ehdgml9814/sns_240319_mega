package com.sns.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	// 파일 삭제
	// input: 이미지 경로
	// output: X
	public void deleteFile(String imagePath) { // /images/aaaa_1721311714837/leaf-8867169_1280.jpg
		// D:\6_spring_project\sns\sns_workspace\images\aaaa_1721311714837/leaf-8867169_1280.jpg
		
		// FILE_UPLOAD_PATH = D:\\6_spring_project\\sns\\sns_workspace\\images/
		// 주소에 겹치는 /images/를 지워야 함
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		
		// 삭제할 이미지 존재하는지 확인
		if (Files.exists(path)) {
			// 이미지 삭제
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[FileManagerService 파일 삭제] 삭제 실패. path:{}", path.toString());
				return;
			}
			
			// 폴더(디렉토리) 삭제
			path = path.getParent();
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[FileManagerService 디렉토리 삭제] 삭제 실패. path:{}", path.toString());
				}
			}
		}
	}
}

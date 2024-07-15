package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtils;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {

	@Autowired
	private UserBO userBO;
	
	/**
	 * 중복확인
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if (user != null) {
			result.put("is_duplicated_id", true);			
		} else {
			result.put("is_duplicated_id", false);			
		}
		return result;
	}
	
	/**
	 * 회원가입
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId, 
			@RequestParam("password") String password, 
			@RequestParam("name") String name, 
			@RequestParam("email") String email) {
		
		String hashedPassword = EncryptUtils.md5(password);
		
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		return result;
	}
	
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId, 
			@RequestParam("password") String password, 
			HttpSession session) {
		
		// 비밀번호 해싱
		String hashedPassword = EncryptUtils.md5(password);
		
		// db 검색
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		// 로그인, 응답
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());

			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 403);
			result.put("error_message", "존재하지 않는 사용자입니다.");
		}
		return result;
	}
}

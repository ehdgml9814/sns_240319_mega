package com.sns.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.user.entity.UserEntity;
import com.sns.user.repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	private UserRepository userRepository;
	
	// input: loginId
	// output: UserEntity 또는 null
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByloginId(loginId);
	}
	
	// input: 파라미터 4개
	// output: UserEntity
	public UserEntity addUser(String loginId, String password, String name, String email) {
		return userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
	}
	
	// input: loginId, password
	// output: UserEntity or null
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);
	}
}

package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	// input: postId, userId
	// output: X
	public void likeToggle(int postId, int userId) {
		// 조회
		int count = likeMapper.selectLikeCountByPoistIdUserId(postId, userId);
		
		// 삭제 or 추가
		if (count > 0) { // 이미 좋아요 되어있음
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else {
			likeMapper.insertLikeByPostIdUserId(postId, userId);
		}
	}
	
	// input: postId
	// output: likeCount
	public int likeCountByPostId(int postId) {
		return likeMapper.selectLikeByPostId(postId);
	}
}

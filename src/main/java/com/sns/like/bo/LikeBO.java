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
		int count = likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
		
		// 삭제 or 추가
		if (count > 0) { // 이미 좋아요 되어있음
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else {
			likeMapper.insertLikeByPostIdUserId(postId, userId);
		}
	}
	
	// input: postId
	// output: likeCount
	// 합친 쿼리로 바꿈
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
	}
	
	// input: postId, userId
	// output: int
	// 합친 쿼리로 바꿈
	public int getLikeCountByPostIdUserId(int postId, int userId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
	}
	
	// 좋아요 채울지 말지
	// input: postId(필수), userId(null 가능)
	// output: boolean
	public boolean filledLikeByPostIdUserId(int postId, Integer userId) {
		
		// 비 로그인이면 DB조회 없이 채우지 않음
		if (userId == null) {
			return false;
		}
		
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) == 1 ? true : false;
	}
}

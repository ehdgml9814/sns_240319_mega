package com.sns.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

//	public int selectLikeCountByPoistIdUserId(
//			@Param("postId") int postId, 
//			@Param("userId") int userId);
	
//	public int selectLikeCountByPostId(int postId);

	// 좋아요 count 통합
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId,
			@Param("userId") Integer userId);

	public void deleteLikeByPostIdUserId(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	
	public void insertLikeByPostIdUserId(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	
}

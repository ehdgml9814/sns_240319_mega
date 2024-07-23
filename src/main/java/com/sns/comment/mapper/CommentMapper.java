package com.sns.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sns.comment.domain.Comment;

@Mapper
public interface CommentMapper {

	public int addComment(
			@Param("postId") int postId, 
			@Param("userId") int userId, 
			@Param("comment") String comment);
	
	public List<Comment> selectCommentList();
	
	public List<Comment> selectCommentListByPostId(int postId);
	
	public void deleteCommentById(int id);
}
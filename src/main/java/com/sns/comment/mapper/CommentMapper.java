package com.sns.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

	public int addComment(
			@Param("postId") int postId, 
			@Param("userId") int userId, 
			@Param("comment") String comment);
}

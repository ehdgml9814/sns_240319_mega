package com.sns.comment.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.mapper.CommentMapper;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;
	
	// 댓글 추가
	// input: postId, userId, comment
	// output: X 
	public void addComment(int postId, int userId, String comment) {
		commentMapper.addComment(postId, userId, comment);
	}
}

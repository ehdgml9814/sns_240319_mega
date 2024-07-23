package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.domain.Comment;
import com.sns.comment.domain.CommentView;
import com.sns.comment.mapper.CommentMapper;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;
	
	// 댓글 추가
	// input: postId, userId, comment
	// output: X 
	public void addComment(int postId, int userId, String comment) {
		commentMapper.addComment(postId, userId, comment);
	}
	
	// input: postId
	// output: List<CommentView>
	public List<CommentView> generateCommentViewListByPostId(int postId) {
		List<CommentView> commentViewList = new ArrayList<>();
		
		// 댓글들 가져옴
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
		
		// 반복문 순회 => Comment -> CommentView --> list에 담음
		for (Comment comment : commentList) {
			CommentView commentView = new CommentView();
			// commentView에 comment 추가
			commentView.setComment(comment);
			// commentView에 user 추가
			UserEntity user = userBO.getUserEntityByUserId(comment.getUserId());
			commentView.setUser(user);
			
			// commentViewList에 commentView 추가!!!!
			commentViewList.add(commentView);
		}
		
		return commentViewList;
	}
	// input: commentId
	// output: X
	public void deleteCommentById(int id) {
		commentMapper.deleteCommentById(id);
	}
}

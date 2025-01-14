package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentView;
import com.sns.like.bo.LikeBO;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardView;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class TimelineBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired 
	private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;

	// input: userId(로그인 한 사용자 번호)
	// output: List<CardView>
	// 비 로그인도 타임라인은 보여짐
	public List<CardView> generateCardViewList(Integer userId) { // 다른걸 가공해서 만들 때 generate
		List<CardView> cardViewList = new ArrayList<>();
		
		// 글 목록을 가져온다 - List<PostEntity>
		List<PostEntity> postEntityList = postBO.getPostEntityList();
		
		// 글 목록 반복문 순회
		// PostEntity => CardView --> cardViewList
		for (PostEntity post : postEntityList) {
			CardView card = new CardView();
			
			// cardView에 글 데이터 추가 
			card.setPost(post);
			
			// cardView에 글쓴이 데이터 추가
			UserEntity user = userBO.getUserEntityByUserId(post.getUserId());
			card.setUser(user);
			
			// cardView에 댓글 N개 데이터 추가
			List<CommentView> commentViewList = commentBO.generateCommentViewListByPostId(post.getId());
			card.setCommentList(commentViewList);
			
			// 좋아요 개수
			int likeCount = likeBO.getLikeCountByPostId(post.getId());
			card.setLikeCount(likeCount);
			
			// 좋아요 여부
			card.setFilledLike(likeBO.filledLikeByPostIdUserId(post.getId(), userId)); 
			
			// cardViewList에 추가!!!!!! 
			cardViewList.add(card);
		}
		
		return cardViewList;
	}
}

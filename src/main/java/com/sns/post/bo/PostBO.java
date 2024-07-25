package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBO {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private CommentBO commentBO;

	public List<PostEntity> getPostEntityList() {
		return postRepository.findByOrderByIdDesc();
	}
	
	public PostEntity addPost(int userId, String userLoginId, 
			String content, MultipartFile file) {
		
		String imagePath = fileManagerService.uploadFile(file, userLoginId);
		
		return postRepository.save(
				PostEntity.builder()
				.userId(userId)
				.content(content)
				.imagePath(imagePath)
				.build());
	}
	
	@Transactional // 일부에서 에러가 났을 때 롤백해주는 어노테이션
	public void deletePostByPostIdUserId(int postId) {
		// 좋아요
		likeBO.deleteLikesByPostId(postId);
		
		// 댓글
		commentBO.deleteCommentsByPostId(postId);
		
		// 기존 글 가져오기
		PostEntity post = postRepository.findById(postId).orElse(null);
		if (post == null) {
			log.info("[글 삭제] post is null postId:{}", postId);
			return;
		}
		
		// 글, 이미지
		if (post != null) {
			postRepository.delete(post);
			if (post.getImagePath() != null) {
				fileManagerService.deleteFile(post.getImagePath());
			}
		}
	}
}

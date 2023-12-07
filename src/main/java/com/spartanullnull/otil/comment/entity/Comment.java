package com.spartanullnull.otil.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spartanullnull.otil.like.entity.Like;
import com.spartanullnull.otil.post.entity.Post;
import com.spartanullnull.otil.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Builder
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String commentText; // 댓글 내용

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

	@JsonIgnore
	@OneToMany(targetEntity = Like.class, mappedBy = "comment", cascade = CascadeType.ALL,orphanRemoval = true)
	private final List<Like> likes = new ArrayList<>();
}
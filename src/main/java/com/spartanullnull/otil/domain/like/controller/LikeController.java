package com.spartanullnull.otil.domain.like.controller;

import com.spartanullnull.otil.domain.like.service.LikeService;
import com.spartanullnull.otil.security.Impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 댓글 좋아요 누르기 API
    @PostMapping("/{postId}/comments/{commentId}/like")
    public ResponseEntity<String> createLike(
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 로그인한 사용자 체크
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인한 사용자만 댓글 좋아요를 누를 수 있습니다.");
        }

        likeService.createLike(userDetails.getUser().getId(), commentId);
        return ResponseEntity.ok("댓글 좋아요 성공!");
    }

    // 댓글 좋아요 취소하기 API
    @DeleteMapping("/{postId}/comments/{commentId}/like")
    public ResponseEntity<String> deleteLike(
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 로그인한 사용자 체크
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인한 사용자만 댓글 좋아요를 취소할 수 있습니다.");
        }

        likeService.deleteLike(userDetails.getUser().getId(), commentId);
        return ResponseEntity.ok("댓글 좋아요 취소!");
    }

    // 댓글 좋아요 개수 조회 API
    @GetMapping("/{postId}/comments/{commentId}/count")
    public ResponseEntity<String> getLikeCount(@PathVariable Long commentId) {
        Long likeCount = likeService.getLikeCount(commentId);
        return ResponseEntity.ok("댓글 좋아요 : " + likeCount);
    }
}
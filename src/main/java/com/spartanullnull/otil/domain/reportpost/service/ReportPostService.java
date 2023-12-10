package com.spartanullnull.otil.domain.reportpost.service;

import com.spartanullnull.otil.domain.reportpost.dto.ReportPostRequestDto;
import com.spartanullnull.otil.domain.reportpost.dto.ReportPostResponseDto;
import com.spartanullnull.otil.domain.reportpost.entity.ReportPost;
import com.spartanullnull.otil.domain.reportpost.repository.ReportPostRepository;
import com.spartanullnull.otil.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportPostService {

    private final ReportPostRepository reportPostRepository;

    public ReportPostResponseDto createReport(ReportPostRequestDto requestDto, User user) {
        ReportPost reportPost = new ReportPost(requestDto);
        reportPost.setUser(user);

        reportPostRepository.save(reportPost);
        return new ReportPostResponseDto(reportPost);
    }

    @Transactional(readOnly = true)
    public Page<ReportPostResponseDto> getUserAllReportPost(User user, int page,
        int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ReportPost> reportPosts = reportPostRepository.findByUser(user, pageable);

        return reportPosts.map(ReportPostResponseDto::new);
    }

    @Transactional
    public Page<ReportPostResponseDto> getAdminAllReportPost(int page, int size,
        String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ReportPost> reportPosts = reportPostRepository.findAll(pageable);

        if (reportPosts != null) {
            return reportPosts.map(ReportPostResponseDto::new);
        } else {
            throw new IllegalArgumentException("신고 게시글이 존재하지 않습니다");
        }
    }

    public ReportPostResponseDto getReport(User user, Long postid) {
        ReportPost reportPost = getByUserAndId(postid, user);
        if (reportPost != null) {
            return new ReportPostResponseDto(reportPost);
        } else {
            throw new IllegalArgumentException("신고 게시글이 존재하지 않습니다.");
        }

    }

    @Transactional
    public ReportPostResponseDto updateReport(Long postid, ReportPostRequestDto requestDto,
        User user) {
        ReportPost reportPost = getByUserAndId(postid, user);
        reportPost.setUser(user);
        reportPost.setName(requestDto.getName());
        reportPost.setTitle(requestDto.getTitle());
        reportPost.setContent(requestDto.getContent());

        if (reportPost != null) {
            reportPostRepository.save(reportPost);
            return new ReportPostResponseDto(reportPost);
        } else {
            throw new IllegalArgumentException("신고 게시글이 존재하지 않습니다");
        }
    }

    @Transactional
    public void deleteReport(Long postid, User user) {
        ReportPost byUserAndId = getByUserAndId(postid, user);
        if (byUserAndId != null) {
            reportPostRepository.deleteById(postid);
        }else{
            throw new IllegalArgumentException("신고 게시글이 존재하지 않습니다");
        }
    }

    private ReportPost getByUserAndId(Long postid, User user) {
        return reportPostRepository.findByUserAndId(user, postid);
    }
}

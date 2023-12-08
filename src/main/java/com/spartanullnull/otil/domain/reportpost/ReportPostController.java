package com.spartanullnull.otil.domain.reportpost;

import com.spartanullnull.otil.domain.reportpost.dto.ReportPostRequestDto;
import com.spartanullnull.otil.domain.reportpost.dto.ReportPostResponseDto;
import com.spartanullnull.otil.global.dto.CommonResponseDto;
import com.spartanullnull.otil.security.Impl.UserDetailsImpl;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportPostController {

    private final ReportPostService reportPostService;

    @PostMapping
    public ResponseEntity<ReportPostResponseDto> createReport(
        @RequestBody ReportPostRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ReportPostResponseDto reportPostResponseDto = reportPostService.createReport(requestDto,
                userDetails.getUser());
            return ResponseEntity.ok().body(reportPostResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/gets")
    public Page<ReportPostResponseDto> getAllReport(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("isAsc") boolean isAsc,
        @RequestParam String password,
        @AuthenticationPrincipal UserDetailsImpl user) {
        return reportPostService.getAllNewsFeeds(password, user.getUser(), page - 1, size, sortBy,
            isAsc);
    }

    @GetMapping("/get/{postid}")
    public ResponseEntity<ReportPostResponseDto> getReport(@PathVariable Long postid,
        @RequestParam String password, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ReportPostResponseDto reportPostResponseDto = reportPostService.getReport(postid,
                password, userDetails.getUser());
            return ResponseEntity.ok().body(reportPostResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{postid}")
    public ResponseEntity<ReportPostResponseDto> putReport(@PathVariable Long postid,
        @RequestParam String password, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ReportPostResponseDto reportPostResponseDto = reportPostService.putReport(postid,
                password, userDetails.getUser());
            return ResponseEntity.ok().body(reportPostResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{postid}")
    public ResponseEntity<CommonResponseDto> deleteReport(@PathVariable Long postid,
        @RequestParam String password, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            reportPostService.deleteReport(postid, password, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}

package com.runninghi.postreport.command.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.postreport.command.application.dto.request.PostReportSaveRequest;
import com.runninghi.postreport.command.application.dto.request.PostReportUpdateRequest;
import com.runninghi.postreport.command.application.dto.response.PostReportDeleteResponse;
import com.runninghi.postreport.command.application.dto.response.PostReportResponse;
import com.runninghi.postreport.command.application.service.PostReportCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostReportCommandController {

    private final PostReportCommandService postReportCommandService;

    // 설명. 게시글 신고 저장 기능
    @PostMapping("/postReports")
    public ApiResponse createPostReport(PostReportSaveRequest request) {

        PostReportResponse response = postReportCommandService.savePostReport(request);

        return ApiResponse.success("성공적으로 등록되었습니다.", response);
    }

    // 설명. 게시글 신고 수락 여부에 따라 신고 처리상태 및 유저정보 변경 기능
    @PutMapping("/postReports/{postReportNo}")
    public ApiResponse updatePostReport(PostReportUpdateRequest request, @PathVariable Long postReportNo) {

        PostReportResponse response = postReportCommandService.updatePostReport(request, postReportNo);

        return ApiResponse.success("성공적으로 처리되었습니다.", response);
    }

    // 설명. 게시글 신고 삭제 기능
    @DeleteMapping("/postReports/{postReportNo}")
    public ApiResponse deletePostReport(@PathVariable Long postReportNo) {

        PostReportDeleteResponse response = postReportCommandService.deletePostReport(postReportNo);

        return ApiResponse.success("성공적으로 삭제되었습니다.", response);
    }
}

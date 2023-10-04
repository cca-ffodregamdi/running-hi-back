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

    @PostMapping("/postReports")
    public ApiResponse createPostReport(PostReportSaveRequest request) {

        PostReportResponse response = postReportCommandService.savePostReport(request);

        return ApiResponse.success("성공적으로 등록되었습니다.", response);
    }

    @PutMapping("/postReports/{postReportNo}")
    public ApiResponse updatePostReport(PostReportUpdateRequest request, @PathVariable Long postReportNo) {

        PostReportResponse response = postReportCommandService.updatePostReport(request, postReportNo);

        return ApiResponse.success("성공적으로 처리되었습니다.", response);
    }

    @DeleteMapping("/postReports/{postReportNo}")
    public ApiResponse deletePostReport(@PathVariable Long postReportNo) {

        PostReportDeleteResponse response = postReportCommandService.deletePostReport(postReportNo);

        return ApiResponse.success("성공적으로 삭제되었습니다.", response);
    }
}

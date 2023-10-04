package com.runninghi.postreport.query.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.postreport.command.application.dto.response.PostReportResponse;
import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.postreport.query.application.service.PostReportQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostReportQueryController {

    private final PostReportQueryService postReportQueryService;

    @GetMapping("/postReports")
    public ApiResponse findAllPostReports() {

        List<PostReportResponse> response = postReportQueryService.getAllPostReports();

        return ApiResponse.success("성공적으로 조회되었습니다.", response);
    }

    @GetMapping("/postReports/{postReportNo}")
    public ApiResponse findPostReportByPostReportNo(@PathVariable Long postReportNo) {

        PostReportResponse response = postReportQueryService.getPostReportByPostReportNo(postReportNo);

        return ApiResponse.success("성공적으로 조회되었습니다.", response);
    }

    @GetMapping("/postReports/{processingStatus}")
    public ApiResponse findPostReportsByProcessingStatus(@PathVariable ProcessingStatus processingStatus) {

        List<PostReportResponse> response = postReportQueryService.getPostReportByProcessingStatus(processingStatus);

        return ApiResponse.success("성공적으로 조회되었습니다.", response);
    }
}

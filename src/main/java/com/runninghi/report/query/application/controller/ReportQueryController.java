package com.runninghi.report.query.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.report.command.application.dto.response.ReportResponse;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.report.query.application.service.ReportQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportQueryController {

    private final ReportQueryService reportQueryService;

    @GetMapping()
    public ApiResponse findAllReports() {

        List<ReportResponse> response = reportQueryService.getAllReports();

        return ApiResponse.success("성공적으로 조회되었습니다.", response);
    }

    @GetMapping("/{reportNo}")
    public ApiResponse findReportByReportNo(@PathVariable Long reportNo) {

        ReportResponse response = reportQueryService.getReportByReportNo(reportNo);

        return ApiResponse.success("성공적으로 조회되었습니다.", response);
    }

    @GetMapping("/{processingStatus}")
    public ApiResponse findReportsByProcessingStatus(@PathVariable ProcessingStatus processingStatus) {

        List<ReportResponse> response = reportQueryService.getReportByProcessingStatus(processingStatus);

        return ApiResponse.success("성공적으로 조회되었습니다.", response);
    }
}

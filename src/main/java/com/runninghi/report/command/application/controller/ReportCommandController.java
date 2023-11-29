package com.runninghi.report.command.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.report.command.application.dto.request.ReportSaveRequest;
import com.runninghi.report.command.application.dto.request.ReportUpdateRequest;
import com.runninghi.report.command.application.dto.response.ReportDeleteResponse;
import com.runninghi.report.command.application.dto.response.ReportResponse;
import com.runninghi.report.command.application.service.ReportCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportCommandController {

    private final ReportCommandService reportCommandService;

    // 설명. 신고 저장 기능
    @PostMapping()
    public ApiResponse createReport(@RequestBody ReportSaveRequest request) {
        // 필기. 토큰 파싱해서 userNo값 받아와서 신고자 컬럼에 넣도록 수정하기!!!

        System.out.println("request = " + request);
        ReportResponse response = reportCommandService.saveReport(request);

        return ApiResponse.success("성공적으로 등록되었습니다.", response);
    }

    // 설명. 신고 수락 여부에 따라 신고 처리상태 및 유저정보 변경 기능
//    @PutMapping("/{reportNo}")
//    public ApiResponse updateReport(ReportUpdateRequest request, @PathVariable Long reportNo) {
//
//        ReportResponse response = reportCommandService.updateReport(request, reportNo);
//
//        return ApiResponse.success("성공적으로 처리되었습니다.", response);
//    }

    // 설명. 신고 삭제 기능
    @DeleteMapping("/{reportNo}")
    public ApiResponse deleteReport(@PathVariable Long reportNo) {

        ReportDeleteResponse response = reportCommandService.deleteReport(reportNo);

        return ApiResponse.success("성공적으로 삭제되었습니다.", response);
    }
}

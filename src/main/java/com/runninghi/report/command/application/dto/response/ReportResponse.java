package com.runninghi.report.command.application.dto.response;

import com.runninghi.report.command.domain.aggregate.entity.Report;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReportResponse(
        @Schema(description = "신고 번호", example = "1")
        Long reportNo,
        @Schema(description = "신고 유형", example = "POST")
        ReportType reportType,
        @Schema(description = "신고 카테고리 번호", example = "1")
        int reportCategoryCode,
        @Schema(description = "신고 내용", example = "욕설")
        String reportContent,
        @Schema(description = "신고 작성 일시", example = "2023-08-18 14:47:10.324255")
        String reportedDate,
        @Schema(description = "신고 처리 상태", example = "처리중")
        ProcessingStatus processingStatus
//        @Schema(description = "신고자 번호", example = "65d5bd82-a758-4999-abd7-e3023c236f16")
//        UUID reportUserNo,
//        @Schema(description = "피신고자 번호", example = "129c5993-2820-4b20-aabd-9d414bb302e8")
//        UUID reportedUserNo,
//        @Schema(description = "게시글 번호", example = "3L")
//        Long reportedPostNo
) {

    public static ReportResponse from(Report report) {

        return new ReportResponse(
                report.getReportNo(),
                report.getReportType(),
                report.getReportCategoryCode(),
                report.getReportContent(),
                report.getReportedDate(),
                report.getProcessingStatus()
//                report.getReportUserVO().getReportUserNo(),
//                report.getReportedUserVO().getReportedUserNo(),
//                report.getReportedContentVO().getReportedPostNo()
        );
    }
}

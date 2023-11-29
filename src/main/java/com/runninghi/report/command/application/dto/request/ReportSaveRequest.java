package com.runninghi.report.command.application.dto.request;

import com.runninghi.report.command.domain.aggregate.entity.enumtype.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ReportSaveRequest(

        @Schema(description = "신고 유형", example = "post")
        String reportType,

        @Schema(description = "게시글 신고 카테고리", example = "1")
        int reportCategoryCode,

        @Schema(description = "게시글 신고 내용", example = "욕설")
        String reportContent,

        @Schema(description = "신고자 번호", example = "3839efc3-5731-4f0c-9461-86844817c483")
        UUID reportUserNo,

        @Schema(description = "피신고자 번호", example = "3839efc3-5731-4f0c-9461-86844817c483")
        UUID reportedUserNo,

        @Schema(description = "신고당한 게시글 번호", example = "1L")
        Long reportedPostNo
) {}

package com.runninghi.report.command.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReportDeleteResponse(

        @Schema(description = "신고 삭제 성공 여부", example = "ture")
        boolean result
) {
}
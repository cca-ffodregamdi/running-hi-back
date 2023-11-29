package com.runninghi.report.command.application.dto.request;

import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReportUpdateRequest(

        @Schema(description = "신고 처리 상태", example = "ACCEPTED")
        ProcessingStatus processingStatus
) {
}

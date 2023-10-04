package com.runninghi.postreport.command.application.dto.request;

import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record PostReportUpdateRequest(

        @Schema(description = "신고 처리 상태", example = "ACCEPTED")
        ProcessingStatus processingStatus
) {
}

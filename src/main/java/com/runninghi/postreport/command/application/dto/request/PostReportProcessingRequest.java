package com.runninghi.postreport.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostReportProcessingRequest(

        @Schema(description = "신고 수락 여부", example = "true")
        boolean isAccepted,

        @Schema(description = "신고 번호", example = "15L")
        Long postReportNo
) {}

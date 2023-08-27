package com.runninghi.postreport.command.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReportedUserResponse(

        @Schema(description = "피신고 횟수", example = "3")
        int reportCount,

        @Schema(description = "블랙리스트 상태", example = "true")
        boolean blacklistStatus
) {}

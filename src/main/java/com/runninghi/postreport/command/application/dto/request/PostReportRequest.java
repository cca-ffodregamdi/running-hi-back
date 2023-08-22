package com.runninghi.postreport.command.application.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;



public record PostReportRequest (

    @Schema(description = "게시글 신고 카테고리", example = "1")
    int postReportCategoryCode,

    @Schema(description = "게시글 신고 내용", example = "욕설")
    String postReportContent
) {}

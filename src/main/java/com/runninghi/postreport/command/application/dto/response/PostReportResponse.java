package com.runninghi.postreport.command.application.dto.response;

import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostReportResponse (
        @Schema(description = "게시글 신고 번호", example = "1L")
        Long postReportNo,
        @Schema(description = "게시글 신고 카테고리 번호", example = "1")
        int postReportCategoryCode,
        @Schema(description = "게시글 신고 내용", example = "욕설")
        String postReportContent,
        @Schema(description = "게시글 신고 작성 일시", example = "2023-08-18 14:47:10.324255")
        LocalDateTime postReportedDate,
        @Schema(description = "게시글 신고자 번호", example = "65d5bd82-a758-4999-abd7-e3023c236f16")
        UUID postReportUserNo,
        @Schema(description = "게시글 피신고자 번호", example = "129c5993-2820-4b20-aabd-9d414bb302e8")
        UUID postReportedUserNo,
        @Schema(description = "신고된 게시글 번호", example = "3L")
        Long reportedPostNo
) {

    public static PostReportResponse from(PostReport postReport) {

        return new PostReportResponse(
                postReport.getPostReportNo(),
                postReport.getPostReportCategoryCode(),
                postReport.getPostReportContent(),
                postReport.getPostReportedDate(),
                postReport.getPostReportUserVO().getPostReportUserNo(),
                postReport.getPostReportedUserVO().getPostReportedUserNo(),
                postReport.getReportedPostVO().getReportedPostNo()
        );
    }
}

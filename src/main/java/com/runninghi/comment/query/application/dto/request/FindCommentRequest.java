package com.runninghi.comment.query.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FindCommentRequest(
        @Schema(description = "댓글 번호", example = "1")
        Long commentNo
) {
    @Override
    public Long commentNo() {
        return commentNo;
    }
}
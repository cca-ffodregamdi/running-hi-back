package com.runninghi.comment.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record DeleteCommentRequest(
        @Schema(description = "댓글 고유번호", example = "123")
        Long commentNo
) {
    @Override
    public Long commentNo() {
        return commentNo;
    }
}

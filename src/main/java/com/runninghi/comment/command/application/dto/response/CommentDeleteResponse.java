package com.runninghi.comment.command.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentDeleteResponse(
        @Schema(description = "댓글 삭제 성공 여부", example = "true")
        boolean result
) {
    @Override
    public boolean result() {
        return result;
    }
}

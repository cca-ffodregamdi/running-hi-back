package com.runninghi.comment.query.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FindAllCommentsRequest(
        @Schema(description = "게시글 번호", example = "1")
        Long userPostNo
) {
}

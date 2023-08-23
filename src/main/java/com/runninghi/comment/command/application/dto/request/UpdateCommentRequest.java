package com.runninghi.comment.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UpdateCommentRequest(

        @Schema(description = "댓글 고유 번호", example="1")
        Long commentNo,
        @Schema(description = "댓글 내용", example = "댓글 내용을 작성하는 부분입니다.")
        String commentContent

) {
    @Override
    public Long commentNo() {
        return commentNo;
    }

    @Override
    public String commentContent() {
        return commentContent;
    }
}

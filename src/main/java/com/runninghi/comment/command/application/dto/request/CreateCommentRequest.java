package com.runninghi.comment.command.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CreateCommentRequest(
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        UUID memberNo,

        @Schema(description = "게시글 번호", example = "1")
        Long memberPostNo,

        @Schema(description = "댓글 내용", example = "댓글 내용을 작성하는 부분입니다.")
        String commentContent
) {

    @Override
    public UUID memberNo() {
        return memberNo;
    }

    @Override
    public Long memberPostNo() {
        return memberPostNo;
    }

    @Override
    public String commentContent() {
        return commentContent;
    }
}

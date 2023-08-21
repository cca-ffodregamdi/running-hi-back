package com.runninghi.comment.command.application.dto.request;

import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CreateCommentRequest(
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        UUID userNo,

        @Schema(description = "게시글 번호", example = "1")
        Long userPostNo,

        @Schema(description = "댓글 내용", example = "댓글 내용을 작성하는 부분입니다.")
        String commentContent
) {

        @Override
        public UUID userNo() {
            return userNo;
        }

        @Override
        public Long userPostNo() {
            return userPostNo;
        }

        @Override
        public String commentContent() {
            return commentContent;
        }
}

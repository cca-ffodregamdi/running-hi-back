package com.runninghi.comment.command.application.dto.request;

import com.runninghi.comment.command.domain.aggregate.vo.CommentUserVO;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateCommentRequest(
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        CommentUserVO userNo,

        @Schema(description = "게시글 번호", example = "1")
        Long userPostNo,

        @Schema(description = "댓글 내용", example = "댓글 내용을 작성하는 부분입니다.")
        String commentContent
) {

        @Override
        public CommentUserVO userNo() {
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

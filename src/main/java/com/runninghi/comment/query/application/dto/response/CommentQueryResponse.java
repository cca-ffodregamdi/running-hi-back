package com.runninghi.comment.query.application.dto.response;

import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.aggregate.vo.CommentUserVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public record CommentQueryResponse(
        @Schema(description = "댓글 고유 번호", example = "1")
        Long commentNo,
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        CommentUserVO userNo,

        @Schema(description = "게시글 번호", example = "1")
        Long userPostNo,

        @Schema(description = "댓글 작성 날짜", example = "2023-08-23")
        Date commentDate,

        @Schema(description = "댓글 내용", example = "댓글 내용을 작성하는 부분입니다.")
        String commentContent
) {

    public static CommentQueryResponse from (Comment comment) {
        return new CommentQueryResponse(
                comment.getCommentNo(),
                comment.getUserNo(),
                comment.getUserPostNo(),
                comment.getCommentDate(),
                comment.getCommentContent()
        );
    }
}
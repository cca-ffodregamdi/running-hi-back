package com.runninghi.comment.query.application.dto.response;

import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.command.domain.aggregate.vo.CommentUserVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record CommentQueryResponse(
        @Schema(description = "댓글 고유 번호", example = "1")
        Long commentNo,
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        UUID userNo,

        @Schema(description = "게시글 번호", example = "1")
        Long userPostNo,

        @Schema(description = "댓글 작성 날짜", example = "2023-08-23")
        Date commentDate,

        @Schema(description = "댓글 내용", example = "댓글 내용을 작성하는 부분입니다.")
        String commentContent,

        @Schema(description = "댓글 신고 상태", example = "false")
        boolean commentStatus,

        @Schema(description = "댓글 수정 날짜", example = "2023-09-09")
        Date updateDate
) {

    public static CommentQueryResponse from (Comment comment) {
        return new CommentQueryResponse(
                comment.getCommentNo(),
                comment.getUserNoVO().getUserNo(),
                comment.getUserPostNo(),
                comment.getCommentDate(),
                comment.getCommentContent(),
                comment.isCommentStatus(),
                comment.getUpdateDate()
        );
    }

    public static List<CommentQueryResponse> from(List<Comment> comments) {
        return comments.stream()
                .map(comment -> new CommentQueryResponse(
                        comment.getCommentNo(),
                        comment.getUserNoVO().getUserNo(),
                        comment.getUserPostNo(),
                        comment.getCommentDate(),
                        comment.getCommentContent(),
                        comment.isCommentStatus(),
                        comment.getUpdateDate()
                ))
                .collect(Collectors.toList());
    }
}
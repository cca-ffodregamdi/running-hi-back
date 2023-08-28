package com.runninghi.comment.command.application.controller;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.dto.request.DeleteCommentRequest;
import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import com.runninghi.comment.command.application.dto.response.CommentCommandResponse;
import com.runninghi.comment.command.application.dto.response.CommentDeleteResponse;
import com.runninghi.comment.command.application.service.CommentCommandService;
import com.runninghi.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 Command API")
@RestController
@RequiredArgsConstructor
public class CommentCommandController {

    private final CommentCommandService commentCommandService;

    @Operation(summary = "댓글 생성 ")
    @PostMapping("/api/v1/comments")
    public ResponseEntity<ApiResponse> createComment(CreateCommentRequest request) {

        CommentCommandResponse response = commentCommandService.createComment(request);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 등록되었습니다.", response));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/api/v1/comments/{commentNo}")
    public ResponseEntity<ApiResponse> deleteComment(DeleteCommentRequest request) {

        CommentDeleteResponse response = commentCommandService.deleteComment(request);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 삭제되었습니다.", response));
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/api/v1/comments/{commentNo}")
    public ResponseEntity<ApiResponse> updateComment(UpdateCommentRequest request) {

        CommentCommandResponse response = commentCommandService.updateComment(request);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 수정되었습니다.", response));
    }
}

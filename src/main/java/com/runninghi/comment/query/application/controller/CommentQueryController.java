package com.runninghi.comment.query.application.controller;

import com.runninghi.comment.command.application.dto.request.CreateCommentRequest;
import com.runninghi.comment.command.application.dto.response.CommentCommandResponse;
import com.runninghi.comment.command.domain.aggregate.entity.Comment;
import com.runninghi.comment.query.application.dto.request.FindAllCommentsRequest;
import com.runninghi.comment.query.application.dto.response.CommentQueryResponse;
import com.runninghi.comment.query.application.service.CommentQueryService;
import com.runninghi.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "댓글 Query API")
@RestController
@RequiredArgsConstructor
public class CommentQueryController {

    private final CommentQueryService commentQueryService;

    @Operation(summary = "전체 댓글 조회")
    @GetMapping("/api/v1/user-posts/{userPostNo}")
    public ApiResponse findAllComment(@PathVariable("userPostNo") Long userPostNo) {

//        Page<Comment> response = commentQueryService.findAllComments(new FindAllCommentsRequest(userPostNo), PageRequest.of(0, 10));

        List<CommentQueryResponse> response = commentQueryService.findAllComments(new FindAllCommentsRequest(userPostNo), PageRequest.of(0, 10));

        return ApiResponse.success("성공적으로 조회되었습니다.", response);
    }
}

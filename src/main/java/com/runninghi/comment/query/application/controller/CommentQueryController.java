package com.runninghi.comment.query.application.controller;

import com.runninghi.comment.query.application.dto.request.FindAllCommentsRequest;
import com.runninghi.comment.query.application.dto.response.CommentQueryResponse;
import com.runninghi.comment.query.application.service.CommentQueryService;
import com.runninghi.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "댓글 Query API")
@RestController
@RequiredArgsConstructor
public class CommentQueryController {

    private final CommentQueryService commentQueryService;

    @Operation(summary = "전체 댓글 조회")
    @GetMapping("/api/v1/user-posts/{memberPostNo}")
    public ApiResponse findAllComment(@PathVariable("memberPostNo") Long memberPostNo) {

//        Page<Comment> response = commentQueryService.findAllComments(new FindAllCommentsRequest(userPostNo), PageRequest.of(0, 10));

        List<CommentQueryResponse> response = commentQueryService.findAllComments(new FindAllCommentsRequest(memberPostNo));

        return ApiResponse.success("성공적으로 조회되었습니다.", response);
    }
}

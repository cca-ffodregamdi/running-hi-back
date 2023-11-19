package com.runninghi.userpost.query.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.userpost.query.application.dto.request.GetUserPostRequest;
import com.runninghi.userpost.query.application.dto.request.GetUserPostsRequest;
import com.runninghi.userpost.query.application.dto.response.GetUserPostResponse;
import com.runninghi.userpost.query.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.query.application.service.UserPostQueryService;
import com.runninghi.userpost.query.infrastructure.service.ApiUserPostQueryInfraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserPostQueryController {

    private final ApiUserPostQueryInfraService apiUserPostQueryInfraService;
    private final UserPostQueryService userPostQueryService;

    // 특정 유저 게시물 조회
    @GetMapping("/user-post/{postNo}")
    public ResponseEntity<ApiResponse> getUserPostByPostNo(@PathVariable("postNo") Long postNo, @RequestBody GetUserPostRequest request) {

        UserPostUserResponse user = apiUserPostQueryInfraService.checkUser(request.userId());

        GetUserPostResponse response = userPostQueryService.getUserPost(request, user, postNo);


        return ResponseEntity.ok(ApiResponse.success("유저 게시물 조회 성공", null));
    }

    // 유저 게시물 목록 조회
    @GetMapping("/user-post")
    public ResponseEntity<ApiResponse> getUserPosts(@RequestBody GetUserPostsRequest request) {

        // 유저 게시판 목록 조회

        return ResponseEntity.ok(ApiResponse.success("유저 게시판 목록 조회 성공", null));
    }

}

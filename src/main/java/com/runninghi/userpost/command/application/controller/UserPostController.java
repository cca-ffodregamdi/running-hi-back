package com.runninghi.userpost.command.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.userpost.command.application.dto.request.UserPostCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserPostController {

    // 유저 게시물 생성
    public ApiResponse createUserPost(UserPostCreateRequest request, List<MultipartFile> images) {
        // 이미지 클라우드에 업로드 : 업로드한 이미지의 url 반환

        // 유저 게시물 생성 : postNo 반환

        // 반환받은 이미지 url과 postNo로 image 생성

        return ApiResponse.success("유저 게시물 생성 성공", null);
    }

    // 유저 게시물 삭제

    // 유저 게시물 수정

}

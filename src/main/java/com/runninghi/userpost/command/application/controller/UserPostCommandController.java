package com.runninghi.userpost.command.application.controller;

import com.runninghi.common.response.ApiResponse;
import com.runninghi.userpost.command.application.dto.request.UserPostCreateRequest;
import com.runninghi.userpost.command.application.dto.request.UserPostDeleteRequest;
import com.runninghi.userpost.command.application.dto.request.UserPostUpdateRequest;
import com.runninghi.userpost.command.application.dto.response.UserPostResponse;
import com.runninghi.userpost.command.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.command.application.service.UserPostCommandService;
import com.runninghi.userpost.command.infrastructure.service.ApiUserPostCommandInfraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserPostCommandController {

    private final UserPostCommandService userPostCommandService;
    private final ApiUserPostCommandInfraService apiUserPostCommandInfraService;

    // 유저 게시물 생성
    @PostMapping("api/v1/user-post")
    public ResponseEntity<ApiResponse> createUserPost(@RequestBody UserPostCreateRequest request, List<MultipartFile> images) {

        UserPostUserResponse user = apiUserPostCommandInfraService.checkUser(request.userId());
        // 이미지 클라우드 스토리지에 저장 -> url 반환

        // 게시글 저장 -> 게시글 No 반환
        UserPostResponse userPostResponse = userPostCommandService.createUserPost(request, user);

        // 반환받은 게시글과 url로 이미지 정보를 데이터베이스에 저장

        return ResponseEntity.ok(ApiResponse.success("유저 게시물을 성공적으로 저장하였습니다.", null));
    }

    // 유저 게시물 수정
    @PutMapping("api/v1/user-post")
    public ResponseEntity<ApiResponse> updateUserPost(@RequestBody UserPostUpdateRequest request,
                                                      @RequestBody List<String> deleteImages,
                                                      @RequestBody List<MultipartFile> createImages) {
        // deleteImages : 받아온 이미지 url을 통해 이미지 삭제 -> 삭제한 이미지의 imageNo 반환

        // deleteImages : imageNo를 통해 이미지 정보 데이터베이스에서 삭제

        // 게시글 수정  -> 게시글 No 반환

        // createImages : 이미지 클라우드 스토리지에 저장 -> url 반환

        // createImages : 반환받은 게시글과 url로 이미지 정보를 데이터베이스에 저장

        return ResponseEntity.ok(ApiResponse.success("유저 게시물을 성공적으로 저장하였습니다.", null));
    }

    // 유저 게시물 삭제
    @DeleteMapping("api/v1/user-post")
    public ResponseEntity<ApiResponse> deleteUserPost(@RequestBody UserPostDeleteRequest request) {
        // userPostNo로 게시글 삭제

        // userPostNo가 포함된 모든 이미지 삭제

        return ResponseEntity.ok(ApiResponse.success("유저 게시물을 성공적으로 저장하였습니다.", null));
    }
}

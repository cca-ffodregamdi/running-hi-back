package com.runninghi.user.command.application.controller;

import com.runninghi.common.annotation.UserAuthorize;
import com.runninghi.common.response.ApiResponse;
import com.runninghi.user.command.application.dto.user.request.UserUpdateRequest;
import com.runninghi.user.command.application.service.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "로그인 후 사용할 수 있는 API")
@RequiredArgsConstructor
@UserAuthorize
@RestController
@RequestMapping("/user")
public class UserCommandController {
    private final UserCommandService userCommandService;

    @Operation(summary = "회원 정보 조회")
    @GetMapping
    public ApiResponse getUserInfo(@AuthenticationPrincipal User user) {
        return ApiResponse.success("조회 성공", userCommandService.findUserInfo(UUID.fromString(user.getUsername())));
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ApiResponse deleteUser(@AuthenticationPrincipal User user) {
        return ApiResponse.success("성공적으로 탈퇴되었습니다.", userCommandService.deleteUser(UUID.fromString(user.getUsername())));
    }

    @Operation(summary = "회원 정보 수정")
    @PutMapping
    public ApiResponse updateUser(@AuthenticationPrincipal User user, @RequestBody UserUpdateRequest request) {
        return ApiResponse.success("성공적으로 수정되었습니다.", userCommandService.updateUser(UUID.fromString(user.getUsername()), request));
    }
}

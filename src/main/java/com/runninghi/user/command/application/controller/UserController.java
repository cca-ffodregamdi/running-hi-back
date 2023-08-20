package com.runninghi.user.command.application.controller;

import com.runninghi.common.annotation.UserAuthorize;
import com.runninghi.common.response.ApiResponse;
import com.runninghi.user.command.application.dto.request.UserUpdateRequest;
import com.runninghi.user.command.application.service.UserService;
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
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원 정보 조회")
    @GetMapping
    public ApiResponse getUserInfo(@AuthenticationPrincipal User user) {
        return ApiResponse.success("조회 성공", userService.getUserInfo(UUID.fromString(user.getUsername())));
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ApiResponse deleteUser(@AuthenticationPrincipal User user) {
        return ApiResponse.success( "성공적으로 탈퇴되었습니다.", userService.deleteUser(UUID.fromString(user.getUsername())));
    }

    @Operation(summary = "회원 정보 수정")
    @PutMapping
    public ApiResponse updateUser(@AuthenticationPrincipal User user, @RequestBody UserUpdateRequest request) {
        return ApiResponse.success("성공적으로 수정되었습니다.", userService.updateUser(UUID.fromString(user.getUsername()), request));
    }
}

package com.runninghi.user.command.application.controller;

import com.runninghi.common.annotation.UserAuthorize;
import com.runninghi.common.response.ApiResponse;
import com.runninghi.user.command.application.dto.user.request.UpdatePasswordRequest;
import com.runninghi.user.command.application.dto.user.request.UserUpdateRequest;
import com.runninghi.user.command.application.service.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "회원용 COMMAND API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class UserCommandController {
    private final UserCommandService userCommandService;

    /* 비밀번호 변경 API */
    @Operation(summary = "비밀번호 변경 API")
    @PostMapping("/update-password")
    public ApiResponse updatePassword(@RequestBody UpdatePasswordRequest request) {
        return ApiResponse.success("비밀번호 변경이 완료되었습니다.", userCommandService.updatePassword(request));
    }

    /* 회원 정보 수정 API */
    @Operation(summary = "회원 정보 수정 API")
    @UserAuthorize
    @PutMapping
    public ApiResponse updateUser(@AuthenticationPrincipal User user, @RequestBody UserUpdateRequest request) {
        return ApiResponse.success("성공적으로 수정되었습니다.", userCommandService.updateUser(UUID.fromString(user.getUsername()), request));
    }

    /* 회원 탈퇴 API */
    @Operation(summary = "회원 탈퇴 API")
    @UserAuthorize
    @DeleteMapping
    public ApiResponse deleteUser(@AuthenticationPrincipal User user) {
        return ApiResponse.success("성공적으로 탈퇴되었습니다.", userCommandService.deleteUser(UUID.fromString(user.getUsername())));
    }
}

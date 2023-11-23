package com.runninghi.user.query.application.controller;

import com.runninghi.common.annotation.AdminAuthorize;
import com.runninghi.common.response.ApiResponse;
import com.runninghi.user.command.application.dto.user.request.FindAccountRequest;
import com.runninghi.user.command.application.dto.user.request.FindPasswordRequest;
import com.runninghi.user.query.application.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "회원용 QUERY API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class UserQueryController {
    private final UserQueryService userQueryService;

    /* 회원 조회 */
    @Operation(summary = "회원 정보 조회 API")
    @AdminAuthorize
    @GetMapping
    public ApiResponse getUserInfo(@AuthenticationPrincipal User user) {
        return ApiResponse.success("조회 성공", userQueryService.findUserInfo(UUID.fromString(user.getUsername())));
    }

    /* 아이디 찾기 API */
    @Operation(summary = "아이디 찾기 API")
    @GetMapping("/find-account")
    public ApiResponse findAccount(@RequestBody FindAccountRequest request) {
        return ApiResponse.success("아이디 찾기 성공", userQueryService.findAccount(request));
    }

    /* 비밀번호 찾기 API */
    @Operation(summary = "비밀번호 찾기 API")
    @GetMapping("/find-password")
    public ApiResponse findMemberByIdAndEmail(@RequestBody FindPasswordRequest request) {
        return ApiResponse.success("비밀번호 찾기 성공", userQueryService.findPassword(request));
    }
}

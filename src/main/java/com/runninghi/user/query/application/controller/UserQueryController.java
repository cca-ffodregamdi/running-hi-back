package com.runninghi.user.query.application.controller;

import com.runninghi.common.annotation.AdminAuthorize;
import com.runninghi.common.response.ApiResponse;
import com.runninghi.user.query.application.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "회원용 QUERY API")
@RequiredArgsConstructor
@AdminAuthorize
@RestController
@RequestMapping("/api/vi/admin")
public class UserQueryController {
    private final UserQueryService userQueryService;

    @Operation(summary = "회원 정보 조회")
    @GetMapping
    public ApiResponse getUserInfo(@AuthenticationPrincipal User user) {
        return ApiResponse.success("조회 성공", userQueryService.findUserInfo(UUID.fromString(user.getUsername())));
    }
}

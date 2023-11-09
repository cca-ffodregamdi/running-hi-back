package com.runninghi.user.command.application.controller;


import com.runninghi.common.response.ApiResponse;
import com.runninghi.user.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.user.command.application.dto.sign_up.request.SignUpRequest;
import com.runninghi.user.command.application.dto.sign_up.request.VerifyDuplicationIdRequest;
import com.runninghi.user.command.application.service.SignCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 가입 및 로그인 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
public class SignCommandController {
    private final SignCommandService signCommandService;

    /* 아이디 중복 확인 API */
    @Operation(summary = "아이디 중복 확인")
    @RequestMapping("/api/v1/verifyDuplicationId")
    public ApiResponse verifyDuplicationId(@RequestBody VerifyDuplicationIdRequest request) {
        return ApiResponse.success("중복 확인 결과가 나왔습니다.", signCommandService.verifyDuplicationId(request));
    }

    /* 회원 가입 API */
    @Operation(summary = "회원 가입")
    @PostMapping("/api/v1/sign-up")
    public ApiResponse signUp(@RequestBody SignUpRequest request) {
        return ApiResponse.success("성공적으로 가입되었습니다.", signCommandService.registUser(request));
    }

    /* 로그인 API */
    @Operation(summary = "로그인")
    @PostMapping("/api/v1/sign-in")
    public ApiResponse signIn(@RequestBody SignInRequest request) {
        return ApiResponse.success("성공적으로 로그인되었습니다.", signCommandService.signIn(request));
    }
}

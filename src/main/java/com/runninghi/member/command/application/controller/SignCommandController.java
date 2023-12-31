package com.runninghi.member.command.application.controller;


import com.runninghi.common.response.ApiResponse;
import com.runninghi.member.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.member.command.application.dto.sign_up.request.SignUpRequest;
import com.runninghi.member.command.application.dto.sign_up.request.VerifyDuplicationIdRequest;
import com.runninghi.member.command.application.dto.sign_up.request.VerifyDuplicationNicknameRequest;
import com.runninghi.member.command.application.service.SignCommandService;
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
@RequestMapping("/api/v1")
public class SignCommandController {
    private final SignCommandService signCommandService;

    /* 아이디 중복 확인 API */
    @Operation(summary = "아이디 중복 확인 API")
    @PostMapping("/verify-duplication-id")
    public ApiResponse verifyDuplicationId(@RequestBody VerifyDuplicationIdRequest request) {
        return ApiResponse.success("중복 확인 결과가 나왔습니다.", signCommandService.verifyDuplicationId(request));
    }

    /* 닉네임 중복 확인 API */
    @Operation(summary = "닉네임 중복 확인 API")
    @PostMapping("/verify-duplication-nickname")
    public ApiResponse verifyDuplicationNickname(@RequestBody VerifyDuplicationNicknameRequest request) {
        return ApiResponse.success("중복 확인 결과가 나왔습니다.", signCommandService.verifyDuplicationNickname(request));
    }

    /* 회원 가입 API */
    @Operation(summary = "회원 가입 API")
    @PostMapping("/sign-up")
    public ApiResponse signUp(@RequestBody SignUpRequest request) {
        return ApiResponse.success("성공적으로 가입되었습니다.", signCommandService.registMember(request));
    }

    /* 로그인 API */
    @Operation(summary = "로그인 API")
    @PostMapping("/sign-in")
    public ApiResponse signIn(@RequestBody SignInRequest request) {
        return ApiResponse.success("성공적으로 로그인되었습니다.", signCommandService.signIn(request));
    }
}

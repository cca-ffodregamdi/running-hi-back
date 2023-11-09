package com.runninghi.adminpost.command.application.dto.response;

import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.query.application.dto.user.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminUserCheckResponse(
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
        UUID id,
        @Schema(description = "회원 아이디", example = "qwerty123")
        String account,
        @Schema(description = "회원 이름", example = "김철수")
        String name,
        @Schema(description = "회원 닉네임", example = "qwe")
        String nickname,
        @Schema(description = "회원 이메일", example = "qwe@qwe.qw")
        String email,
        @Schema(description = "카카오 아이디", example = "032109312")
        String kakaoId,
        @Schema(description = "카카오 닉네임", example = "asd")
        String kakaoName,
        @Schema(description = "위치", example = "서울특별시 성동구")
        String location,
        @Schema(description = "성별", example = "외계인")
        String gender,
        @Schema(description = "연령대", example = "20~29")
        String age,
        @Schema(description = "피신고 횟수", example = "11")
        int reportCount,
        @Schema(description = "블랙리스트 상태", example = "false")
        boolean blackListStatus,
        @Schema(description = "회원 상태", example = "true")
        boolean status,
        @Schema(description = "회원 타입", example = "USER")
        Role role,
        @Schema(description = "회원 생성일", example = "2023-08-11T20:02:11")
        LocalDateTime createdAt
) {
    public static UserInfoResponse of(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getAccount(),
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getKakaoId(),
                user.getKakaoName(),
                user.getLocation(),
                user.getGender(),
                user.getAge(),
                user.getReportCount(),
                user.isBlacklistStatus(),
                user.isStatus(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}

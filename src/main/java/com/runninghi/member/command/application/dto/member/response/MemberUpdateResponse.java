package com.runninghi.member.command.application.dto.member.response;

import com.runninghi.member.command.domain.aggregate.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberUpdateResponse(
        @Schema(description = "회원 정보 수정 성공 여부", example = "true")
        boolean result,
        @Schema(description = "회원 닉네임", example = "qwe")
        String nickname
) {
    public static MemberUpdateResponse of(boolean result, Member member) {
        return new MemberUpdateResponse(result, member.getNickname());
    }
}

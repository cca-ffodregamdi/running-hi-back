package com.runninghi.member.command.application.dto.member;

import com.runninghi.member.command.domain.aggregate.entity.MemberRefreshToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenDTO {
    private Optional<MemberRefreshToken> memberRefreshToken;
}

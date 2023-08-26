package com.runninghi.user.command.application.dto.user;

import com.runninghi.user.command.domain.aggregate.entity.UserRefreshToken;
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
    private Optional<UserRefreshToken> userRefreshToken;
}

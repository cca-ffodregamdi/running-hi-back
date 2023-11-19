package com.runninghi.userpost.command.domain.aggregate.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVO {

    @Column
    private UUID userId;

    @Column
    private String nickname;

    public UserVO userVo(UUID userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;

        return this;
    }
}

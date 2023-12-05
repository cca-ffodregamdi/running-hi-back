package com.runninghi.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tbl_member_refresh_token")
public class MemberRefreshToken {
    @Id
    private UUID memberId;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;
    private String refreshToken;
    private int reissueCount = 0;

    public MemberRefreshToken(Member member, String refreshToken) {
        this.member = member;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean validateRefreshToken(String refreshToken) {
        return this.refreshToken.equals(refreshToken);
    }

    public void increaseReissueCount() {
        reissueCount++;
    }
}

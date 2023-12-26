package com.runninghi.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "tbl_member_refresh_token")
@Builder
@Getter
public class MemberRefreshToken {
    // @Id
    // private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;
    private String refreshToken;
    private int reissueCount;

    protected MemberRefreshToken() {

    }

    private MemberRefreshToken(final Member member, final String refreshToken, final int reissueCount) {
        // this.id = id;
        this.member = member;
        this.refreshToken = refreshToken;
        this.reissueCount = reissueCount;
    }

    public static MemberRefreshToken from(Member member, String refreshToken) {
        return MemberRefreshToken.builder()
                .member(member)
                .refreshToken(refreshToken)
                .build();
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

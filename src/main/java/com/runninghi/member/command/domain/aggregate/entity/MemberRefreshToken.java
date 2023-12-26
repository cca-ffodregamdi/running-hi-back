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
    @Id
    private UUID memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;
    private String refreshToken;
    private int reissueCount;

    protected MemberRefreshToken() {

    }

    MemberRefreshToken 엔티티 클래스를 보니, member 필드를 엔티티의 식별자로 사용하려는 것 같습니다. member 필드는 Member 엔티티를 참조하며, @OneToOne 및 @MapsId 어노테이션으로 표시되어 있습니다. 그러나 @MapsId 어노테이션을 사용할 때는 식별자 필드가 필요하며, 이 필드는 @Id 어노테이션으로 표시되어야 합니다.

따라서 MemberRefreshToken 엔티티 클래스에 식별자 필드를 추가해야 합니다. 이 필드의 타입은 Member 엔티티의 식별자 필드와 같아야 합니다. 아래에 이를 반영한 코드를 제시하였습니다:

java
@Entity
@Table(name = "tbl_member_refresh_token")
@Builder
@Getter
public class MemberRefreshToken {

    @Id
    private UUID memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;

    private String refreshToken;
    private int reissueCount;

    protected MemberRefreshToken() {

    }

    private MemberRefreshToken(final UUID memberId, final Member member, final String refreshToken, final int reissueCount) {
        this.memberId = memberId;
        this.member = member;
        this.refreshToken = refreshToken;
        this.reissueCount = reissueCount;
    }

    public static MemberRefreshToken from(Member member, String refreshToken) {
        return MemberRefreshToken.builder()
                .memberId(member.getId())  // Member 클래스의 getId() 메소드가 식별자를 반환한다고 가정
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

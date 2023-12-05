package com.runninghi.member.command.domain.aggregate.entity;

import com.runninghi.common.entity.BaseEntity;
import com.runninghi.member.command.application.dto.member.request.MemberUpdateRequest;
import com.runninghi.member.command.application.dto.member.request.UpdatePasswordRequest;
import com.runninghi.member.command.application.dto.sign_up.request.SignUpRequest;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Entity
@Table(name = "tbl_member")
@Builder
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; // 회원 고유 값

    @Column(nullable = false, scale = 20, unique = true)
    private String account; // 일반 로그인 아이디
    @Column(nullable = false)
    private String password; // 일반 로그인 비밀번호
    @Column(nullable = false)
    private String name; // 일반 로그인 이름
    @Column(nullable = false)
    private String nickname; // 일반 로그인 닉네임
    @Column(nullable = false)
    private String email; // 일반 로그인 이메일
    private String kakaoId; // 카카오 고유 아이디
    private String kakaoName; // 카카오 닉네임
    private String location; // 위치
    private String gender; // 성별
    private String age; // 연령대
    private int reportCount; // 피신고 횟수
    private boolean blacklistStatus; // 블랙리스트 상태
    private boolean status; // 회원 상태 (true = 회원, false = 삭제)

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.GUEST; // ADMIN or USER
    private String provider; // 어떤 OAuth인지 (google, naver 등)
    private String provideId; // 해당 OAuth 의 key(id)

    protected Member() {
    }

    private Member(final UUID id, final String account, final String password,
                   final String name, final String nickname, final String email,
                   final String kakaoId, final String kakaoName, final String location,
                   final String gender, final String age, final int reportCount,
                   final boolean blacklistStatus, final boolean status, final Role role,
                   final String provider, final String provideId) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.kakaoId = kakaoId;
        this.kakaoName = kakaoName;
        this.location = location;
        this.gender = gender;
        this.age = age;
        this.reportCount = reportCount;
        this.blacklistStatus = blacklistStatus;
        this.status = status;
        this.role = role;
        this.provider = provider;
        this.provideId = provideId;
    }

    public static Member from(SignUpRequest request, PasswordEncoder encoder) {
        return Member.builder()
                .account(request.account())
                .password(encoder.encode(request.password()))
                .name(request.name())
                .nickname(request.nickname())
                .email(request.email())
                .role(Role.USER)
                .status(true)
                .build();
    }

    public void updatePassword(UpdatePasswordRequest request, PasswordEncoder encoder) {
        this.password = encoder.encode(request.password());
    }

    public void update(MemberUpdateRequest newUser, PasswordEncoder encoder) {
        this.password = newUser.newPassword() == null || newUser.newPassword().isBlank()
                ? this.password : encoder.encode(newUser.newPassword());
        this.nickname = newUser.nickname();
    }
}
package com.runninghi.user.command.domain.aggregate.entity;

import com.runninghi.common.entity.BaseEntity;
import com.runninghi.user.command.application.dto.sign_up.request.SignUpRequest;
import com.runninghi.user.command.application.dto.user.request.UserUpdateRequest;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Table(name = "TBL_USER")
public class User extends BaseEntity {
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
    private String email; // 일반 로그인 이메일
    @Column(nullable = false)
    private String nickname; // 일반 로그인 닉네임
    private String kakaoId; // 카카오 고유 아이디
    private String kakaoName; // 카카오 닉네임
    private String location; // 위치
    private String gender; // 성별
    private String age; // 연령대
    private int reportCount; // 피신고 횟수
    private boolean blacklistStatus; // 블랙리스트 상태
    private boolean status; // 회원 상태 (true = 회원, false = 삭제)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // ADMIN or USER
    private String provider; // 어떤 OAuth인지 (google, naver 등)
    private String provideId; // 해당 OAuth 의 key(id)

    @Builder
    public User(UUID id, String account, String password, String name, String nickname, String email, String kakaoId, String kakaoName, String location, String gender, String age, int reportCount, boolean blacklistStatus, boolean status, Role role, String provider, String provideId) {
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

    public static User from(SignUpRequest request, PasswordEncoder encoder) {
        return User.builder()
                .account(request.account())
                .password(encoder.encode(request.password()))
                .name(request.name())
                .nickname(request.nickname())
                .email(request.email())
                .role(Role.USER)
                .status(true)
                .build();
    }

    public void update(UserUpdateRequest newUser, PasswordEncoder encoder) {
        this.password = newUser.newPassword() == null || newUser.newPassword().isBlank()
                ? this.password : encoder.encode(newUser.newPassword());
        this.nickname = newUser.nickname();
    }
}

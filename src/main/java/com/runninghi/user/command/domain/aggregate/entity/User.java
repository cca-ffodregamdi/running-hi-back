package com.runninghi.user.command.domain.aggregate.entity;

import com.runninghi.common.entity.BaseEntity;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Table(name = "TBL_USER")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    private String kakaoId;
    private String email;
    private String kakaoName;
    private String name; // 나중에 폼 회원가입 받을 때 사용
    private String pw; // 나중에 폼 회원가입 받을 때 사용
    private String nickname; // 나중에 폼 회원가입 받을 때 사용
    private String location; // 추가로 현 위치 받을 때
    private int reportCount;
    private boolean blacklistStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String provider; // 어떤 OAuth인지 (google, naver 등)
    private String provideId; // 해당 OAuth 의 key(id)
}

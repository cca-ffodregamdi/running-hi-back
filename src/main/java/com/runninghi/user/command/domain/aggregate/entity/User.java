package com.runninghi.user.command.domain.aggregate.entity;

import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Table(name = "TBL_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    @Column
    private String kakaoId;
    @Column
    private String email;
    @Column
    private String kakaoName;
    @Column
    private String name; // 나중에 폼 회원가입 받을 때 사용
    @Column
    private String pw; // 나중에 폼 회원가입 받을 때 사용
    @Column
    private String nickName; // 나중에 폼 회원가입 받을 때 사용
    @Column
    private int report_count;
    @Column
    private boolean blacklist_status;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

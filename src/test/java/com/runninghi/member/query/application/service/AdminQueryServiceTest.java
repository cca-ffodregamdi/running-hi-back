package com.runninghi.member.query.application.service;


import com.runninghi.member.command.domain.aggregate.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class AdminQueryServiceTest {
    @Autowired
    private AdminQueryService adminQueryService;
    @Autowired
    private MemberCommandRepository memberCommandRepository;
    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    @AfterEach
    void clear() {
        memberCommandRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("모든 회원 정보 조회 테스트 : success")
    void findAllUserTest() {
        // given
        memberCommandRepository.save(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());
        memberCommandRepository.save(Member.builder()
                .account("asdfg1234")
                .password(encoder.encode("1234"))
                .name("나철수")
                .nickname("asd")
                .email("asd@asd.as")
                .role(Role.USER)
                .status(true)
                .build());
        memberCommandRepository.save(Member.builder()
                .account("zxcvb1234")
                .password(encoder.encode("1234"))
                .name("박철수")
                .nickname("zxc")
                .email("zxc@zxc.zx")
                .role(Role.USER)
                .status(true)
                .build());
        // when
        List<MemberInfoResponse> users = adminQueryService.findAllUsers();
        // then
        Assertions.assertThat(users).hasSize(3);
        for (MemberInfoResponse user : users) {
            if (user.account().equals("qwerty1234")) {
                Assertions.assertThat(user.account()).isEqualTo("qwerty1234");
                Assertions.assertThat(user.name()).isEqualTo("김철수");
                Assertions.assertThat(user.nickname()).isEqualTo("qwe");
            }
            if (user.account().equals("asdfg1234")) {
                Assertions.assertThat(user.account()).isEqualTo("asdfg1234");
                Assertions.assertThat(user.name()).isEqualTo("나철수");
                Assertions.assertThat(user.nickname()).isEqualTo("asd");
            }
            if (user.account().equals("zxcvb1234")) {
                Assertions.assertThat(user.account()).isEqualTo("zxcvb1234");
                Assertions.assertThat(user.name()).isEqualTo("박철수");
                Assertions.assertThat(user.nickname()).isEqualTo("zxc");
            }
            Assertions.assertThat(user.role()).isEqualTo(Role.USER);
        }
    }

    @Test
    @DisplayName("모든 관리자 정보 조회 테스트 : success")
    void findAllAdminTest() {
        // given
        memberCommandRepository.save(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.ADMIN)
                .status(true)
                .build());
        memberCommandRepository.save(Member.builder()
                .account("asdfg1234")
                .password(encoder.encode("1234"))
                .name("나철수")
                .nickname("asd")
                .email("asd@asd.as")
                .role(Role.ADMIN)
                .status(true)
                .build());
        memberCommandRepository.save(Member.builder()
                .account("zxcvb1234")
                .password(encoder.encode("1234"))
                .name("박철수")
                .nickname("zxc")
                .email("zxc@zxc.zx")
                .role(Role.ADMIN)
                .status(true)
                .build());
        // when
        List<MemberInfoResponse> admins = adminQueryService.findAllAdmins();

        // then
        Assertions.assertThat(admins).hasSize(3);
        for (MemberInfoResponse admin : admins) {
            if (admin.account().equals("qwerty1234")) {
                Assertions.assertThat(admin.account()).isEqualTo("qwerty1234");
                Assertions.assertThat(admin.name()).isEqualTo("김철수");
                Assertions.assertThat(admin.nickname()).isEqualTo("qwe");
            }
            if (admin.account().equals("asdfg1234")) {
                Assertions.assertThat(admin.account()).isEqualTo("asdfg1234");
                Assertions.assertThat(admin.name()).isEqualTo("나철수");
                Assertions.assertThat(admin.nickname()).isEqualTo("asd");
            }
            if (admin.account().equals("zxcvb1234")) {
                Assertions.assertThat(admin.account()).isEqualTo("zxcvb1234");
                Assertions.assertThat(admin.name()).isEqualTo("박철수");
                Assertions.assertThat(admin.nickname()).isEqualTo("zxc");
            }
            Assertions.assertThat(admin.role()).isEqualTo(Role.ADMIN);
        }
    }
}

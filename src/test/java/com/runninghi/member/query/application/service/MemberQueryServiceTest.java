package com.runninghi.member.query.application.service;

import com.runninghi.member.command.domain.aggregate.entity.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberQueryServiceTest {
    @Autowired
    private MemberQueryService memberQueryService;
    @Autowired
    private MemberCommandRepository memberCommandRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Test
    @DisplayName("회원 조회 테스트 : success")
    void findMemberTest() {
        // given
        Member savedMember = memberCommandRepository.save(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());
        // when
        MemberInfoResponse response = memberQueryService.findMemberInfo(savedMember.getId());
        // then
        assertThat(response.id()).isEqualTo(savedMember.getId());
        assertThat(response.account()).isEqualTo("qwerty1234");
        assertThat(response.name()).isEqualTo("김철수");
        assertThat(response.nickname()).isEqualTo("qwe");
        assertThat(response.email()).isEqualTo("qwe@qwe.qw");
        assertThat(response.role()).isEqualTo(Role.USER);
    }
}

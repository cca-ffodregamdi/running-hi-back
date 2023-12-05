package com.runninghi.member.command.application.service;

import com.runninghi.member.command.application.dto.member.request.MemberUpdateRequest;
import com.runninghi.member.command.application.dto.member.response.MemberDeleteResponse;
import com.runninghi.member.command.application.dto.member.response.MemberUpdateResponse;
import com.runninghi.member.command.domain.aggregate.entity.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import com.runninghi.member.query.application.service.MemberQueryService;
import com.runninghi.member.query.infrastructure.repository.MemberQueryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class MemberCommandServiceTest {
    @Autowired
    private MemberCommandService memberCommandService;
    @Autowired
    private MemberQueryService memberQueryService;
    @Autowired
    private MemberCommandRepository memberCommandRepository;
    @Autowired
    private MemberQueryRepository memberQueryRepository;
    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    @AfterEach
    void clear() {
        memberCommandRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원 조회 테스트 : 존재하지 않는 회원 예외처리")
    void doesNotExistUserTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> memberQueryService.findMemberInfo(UUID.randomUUID()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    @DisplayName("회원 탈퇴 테스트 : success")
    void deleteUserTest() {
        // given
        Member savedUser = memberCommandRepository.save(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());
        // when
        MemberDeleteResponse result = memberCommandService.deleteUser(savedUser.getId());
        // then
        List<Member> members = memberQueryRepository.findAll();
        assertThat(members).isEmpty();
        assertThat(result.result()).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트 : success")
    void updateUserTest() {
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
        MemberUpdateRequest request = new MemberUpdateRequest("1234", "5678", "qwe");
        MemberUpdateResponse result = memberCommandService.updateUser(savedMember.getId(), request);
        // then
        assertThat(result.result()).isEqualTo(true);
        assertThat(result.nickname()).isEqualTo("qwe");
        Member member = memberQueryRepository.findAll().get(0);
        assertThat(encoder.matches("5678", member.getPassword())).isEqualTo(true);
    }
}

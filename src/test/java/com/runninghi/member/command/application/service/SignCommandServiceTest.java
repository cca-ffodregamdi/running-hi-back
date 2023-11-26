package com.runninghi.member.command.application.service;

import com.runninghi.member.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.member.command.application.dto.sign_in.response.SignInResponse;
import com.runninghi.member.command.application.dto.sign_up.request.SignUpRequest;
import com.runninghi.member.command.application.dto.sign_up.request.VerifyDuplicationIdRequest;
import com.runninghi.member.command.application.dto.sign_up.response.SignUpResponse;
import com.runninghi.member.command.application.dto.sign_up.response.VerifyDuplicationIdResponse;
import com.runninghi.member.command.domain.aggregate.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import com.runninghi.member.query.infrastructure.repository.MemberQueryRefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@DisplayName("회원가입/로그인 서비스 테스트")
class SignCommandServiceTest {
    @Autowired
    private SignCommandService signCommandService;
    @Autowired
    private MemberCommandRepository memberCommandRepository;
    @Autowired
    private MemberQueryRefreshTokenRepository memberQueryRefreshTokenRepository;
    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void clear() {
        memberQueryRefreshTokenRepository.deleteAllInBatch();
        memberCommandRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원가입 테스트 : 아이디 중복 아닐 경우")
    void verifyDuplicationIdTrue() {
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
        // when
        VerifyDuplicationIdRequest request = new VerifyDuplicationIdRequest("qwerty123");
        VerifyDuplicationIdResponse response = signCommandService.verifyDuplicationId(request);
        // then
        assertThat(response.result()).isTrue();
    }

    @Test
    @DisplayName("회원가입 테스트 : 아이디 중복일 경우")
    void verifyDuplicationIdFalse() {
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
        // when
        VerifyDuplicationIdRequest request = new VerifyDuplicationIdRequest("qwerty1234");
        VerifyDuplicationIdResponse response = signCommandService.verifyDuplicationId(request);
        // then
        assertThat(response.result()).isFalse();
    }


    @Test
    @DisplayName("회원가입 테스트 : success")
    void signUpTest() {
        // given
        SignUpRequest request = new SignUpRequest("qwerty1234", "1234", "김철수", "qwe", "qwe@qwe.qw");
        // when
        SignUpResponse response = signCommandService.registUser(request);
        // then
        assertAll(
                () -> assertThat(response.account()).isEqualTo("qwerty1234"),
                () -> assertThat(response.name()).isEqualTo("김철수")
        );
    }

    @Test
    @DisplayName("회원가입 테스트 : 아이디 중복 시 예외처리")
    void duplicateAccountTest() {
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
        // when
        SignUpRequest request = new SignUpRequest("qwerty1234", "1234", "김철수", "qwe", "qwe@qwe.qw");
        // then
        assertThatThrownBy(() -> signCommandService.registUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 아이디입니다.");
    }

    @Test
    @DisplayName("로그인 테스트 : success")
    void signInTest() {
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
        // when
        SignInResponse response = signCommandService.signIn(new SignInRequest("qwerty1234", "1234"));
        // then
        assertAll(
                () -> assertThat(response.name()).isEqualTo("김철수"),
                () -> assertThat(response.role()).isEqualTo(Role.USER)
        );
    }

    @Test
    @DisplayName("로그인 테스트 : 아이디/비밀번호 불일치 시 예외처리")
    void failLoginTest() {
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
        // when, then
        assertThatThrownBy(() -> signCommandService.signIn(new SignInRequest("qwerty1234", "12345")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
}

package com.runninghi.user.command.application.service;

import com.runninghi.user.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.user.command.application.dto.sign_in.response.SignInResponse;
import com.runninghi.user.command.application.dto.sign_up.request.SignUpRequest;
import com.runninghi.user.command.application.dto.sign_up.response.SignUpResponse;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
import com.runninghi.user.query.infrastructure.repository.UserQueryRefreshTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SignCommandServiceTest {
    @Autowired
    private SignCommandService signCommandService;
    @Autowired
    private UserCommandRepository userCommandRepository;
    @Autowired
    private UserQueryRefreshTokenRepository userQueryRefreshTokenRepository;
    @Autowired
    private PasswordEncoder encoder;
    
    @BeforeEach
    @AfterEach
    void clear() {
        userQueryRefreshTokenRepository.deleteAllInBatch();
        userCommandRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원가입 테스트 : success")
    void signUpTest() {
        // given
        SignUpRequest request = new SignUpRequest("qwerty1234", "1234", "김철수", "qwe", "qwe@qwe.qw");
        // when
        SignUpResponse response = signCommandService.registUser(request);
        // then
        Assertions.assertThat(response.account()).isEqualTo("qwerty1234");
        Assertions.assertThat(response.name()).isEqualTo("김철수");
    }

    @Test
    @DisplayName("회원가입 테스트 : 아이디 중복 시 예외처리")
    void duplicateAccountTest() {
        // given
        userCommandRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());
        SignUpRequest request = new SignUpRequest("qwerty1234", "1234", "김철수", "qwe", "qwe@qwe.qw");
        // when
        // then
        Assertions.assertThatThrownBy(() -> signCommandService.registUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 아이디입니다.");
    }

    @Test
    @DisplayName("로그인 테스트 : success")
    void signInTest() {
        // given
        userCommandRepository.save(User.builder()
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
        Assertions.assertThat(response.name()).isEqualTo("김철수");
        Assertions.assertThat(response.role()).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("로그인 테스트 : 아이디/비밀번호 불일치 시 예외처리")
    void failLoginTest() {
        // given
        userCommandRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());
        // when
        // then
        Assertions.assertThatThrownBy(() -> signCommandService.signIn(new SignInRequest("qwerty1234", "12345")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
}

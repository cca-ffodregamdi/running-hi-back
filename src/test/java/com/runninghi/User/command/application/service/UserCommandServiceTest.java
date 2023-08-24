package com.runninghi.User.command.application.service;

import com.runninghi.user.command.application.dto.user.request.UserUpdateRequest;
import com.runninghi.user.command.application.dto.user.response.UserDeleteResponse;
import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.command.application.dto.user.response.UserUpdateResponse;
import com.runninghi.user.command.application.service.UserCommandService;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
import com.runninghi.user.query.application.service.UserQueryService;
import com.runninghi.user.query.infrastructure.repository.UserQueryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
public class UserCommandServiceTest {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordEncoder encoder;

    @Autowired
    UserCommandServiceTest(UserCommandService userCommandService, UserQueryService userQueryService, UserCommandRepository userCommandRepository, UserQueryRepository userQueryRepository, PasswordEncoder encoder) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.userCommandRepository = userCommandRepository;
        this.userQueryRepository = userQueryRepository;
        this.encoder = encoder;
    }

    @BeforeEach
    @AfterEach
    void clear() {
        userCommandRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원 조회 테스트 : success")
    void findUserTest() {
        // given
        User savedUser = userCommandRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());
        // when
        UserInfoResponse response = userQueryService.findUserInfo(savedUser.getId());
        // then
        assertThat(response.id()).isEqualTo(savedUser.getId());
        assertThat(response.account()).isEqualTo("qwerty1234");
        assertThat(response.name()).isEqualTo("김철수");
        assertThat(response.nickname()).isEqualTo("qwe");
        assertThat(response.email()).isEqualTo("qwe@qwe.qw");
        assertThat(response.role()).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("회원 조회 테스트 : 존재하지 않는 회원 예외처리")
    void doesNotExistUserTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> userQueryService.findUserInfo(UUID.randomUUID()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    @DisplayName("회원 탈퇴 테스트 : success")
    void deleteUserTest() {
        // given
        User savedUser = userCommandRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());
        // when
        UserDeleteResponse result = userCommandService.deleteUser(savedUser.getId());
        // then
        List<User> users = userQueryRepository.findAll();
        assertThat(users).isEmpty();
        assertThat(result.result()).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트 : success")
    void updateUserTest() {
        // given
        User savedUser = userCommandRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());
        // when
        UserUpdateRequest request = new UserUpdateRequest("1234", "5678", "qwe");
        UserUpdateResponse result = userCommandService.updateUser(savedUser.getId(), request);
        // then
        assertThat(result.result()).isEqualTo(true);
        assertThat(result.nickname()).isEqualTo("qwe");
        User user = userQueryRepository.findAll().get(0);
        assertThat(encoder.matches("5678", user.getPassword())).isEqualTo(true);
    }
}

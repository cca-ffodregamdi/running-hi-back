package com.runninghi.User.command.application.service;

import com.runninghi.user.command.application.dto.user.request.UserUpdateRequest;
import com.runninghi.user.command.application.dto.user.response.UserDeleteResponse;
import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.command.application.dto.user.response.UserUpdateResponse;
import com.runninghi.user.command.application.service.UserService;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
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
public class UserServiceTest {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    UserServiceTest(UserService userService, UserRepository userRepository, PasswordEncoder encoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @BeforeEach
    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 조회 테스트 : success")
    void findUserTest() {
        // given
        User savedUser = userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("qweqwe")
                .role(Role.USER)
                .build());
        // when
        UserInfoResponse response = userService.getUserInfo(savedUser.getId());
        // then
        assertThat(response.id()).isEqualTo(savedUser.getId());
        assertThat(response.account()).isEqualTo("qwerty1234");
        assertThat(response.name()).isEqualTo("qweqwe");
        assertThat(response.role()).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("회원 조회 테스트 : 존재하지 않는 회원 예외처리")
    void doesNotExistUserTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> userService.getUserInfo(UUID.randomUUID()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    @DisplayName("회원 탈퇴 테스트 : success")
    void deleteUserTest() {
        // given
        User savedUser = userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("qweqwe")
                .role(Role.USER)
                .build());
        // when
        UserDeleteResponse result = userService.deleteUser(savedUser.getId());
        // then
        List<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
        assertThat(result.result()).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트 : success")
    void updateUserTest() {
        // given
        User savedUser = userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("qweqwe")
                .role(Role.USER)
                .build());
        // when
        UserUpdateRequest request = new UserUpdateRequest("1234", "5678", "qweqwe");
        UserUpdateResponse result = userService.updateUser(savedUser.getId(), request);
        // then
        assertThat(result.result()).isEqualTo(true);
        assertThat(result.name()).isEqualTo("qweqwe");
        User user = userRepository.findAll().get(0);
        assertThat(encoder.matches("5678", user.getPassword())).isEqualTo(true);
    }
}

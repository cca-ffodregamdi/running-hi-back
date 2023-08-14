package com.runninghi.User.command.application.service;


import com.runninghi.user.command.application.dto.response.UserInfoResponse;
import com.runninghi.user.command.application.service.AdminService;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class AdminServiceTest {
    private final AdminService adminService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    AdminServiceTest(AdminService adminService, UserRepository userRepository, PasswordEncoder encoder) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @BeforeEach
    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 회원 정보 조회 테스트")
    void findAllUserTest() {
        // given
        userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("qweqwe")
                .role(Role.USER)
                .build());
        userRepository.save(User.builder()
                .account("asdfg1234")
                .password(encoder.encode("1234"))
                .name("asdasd")
                .role(Role.USER)
                .build());
        userRepository.save(User.builder()
                .account("zxcvb1234")
                .password(encoder.encode("1234"))
                .name("zxczxc")
                .role(Role.USER)
                .build());
        // when
        List<UserInfoResponse> users = adminService.getUsers();
        // then
        Assertions.assertThat(users).hasSize(3);
        Assertions.assertThat(users.get(0).account()).isEqualTo("qwerty1234");
        Assertions.assertThat(users.get(0).name()).isEqualTo("qweqwe");
        Assertions.assertThat(users.get(0).role()).isEqualTo(Role.USER);
        Assertions.assertThat(users.get(1).account()).isEqualTo("asdfg1234");
        Assertions.assertThat(users.get(1).name()).isEqualTo("asdasd");
        Assertions.assertThat(users.get(1).role()).isEqualTo(Role.USER);
        Assertions.assertThat(users.get(2).account()).isEqualTo("zxcvb1234");
        Assertions.assertThat(users.get(2).name()).isEqualTo("zxczxc");
        Assertions.assertThat(users.get(2).role()).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("모든 관리자 정보 조회 테스트")
    void findAllAdminTest() {
        // given
        userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("qweqwe")
                .role(Role.ADMIN)
                .build());
        userRepository.save(User.builder()
                .account("asdfg1234")
                .password(encoder.encode("1234"))
                .name("asdasd")
                .role(Role.ADMIN)
                .build());
        userRepository.save(User.builder()
                .account("zxcvb1234")
                .password(encoder.encode("1234"))
                .name("zxczxc")
                .role(Role.ADMIN)
                .build());
        // when
        List<UserInfoResponse> admins = adminService.getAdmins();
        // then
        Assertions.assertThat(admins).hasSize(3);
        Assertions.assertThat(admins.get(0).account()).isEqualTo("qwerty1234");
        Assertions.assertThat(admins.get(0).name()).isEqualTo("qweqwe");
        Assertions.assertThat(admins.get(0).role()).isEqualTo(Role.ADMIN);
        Assertions.assertThat(admins.get(1).account()).isEqualTo("asdfg1234");
        Assertions.assertThat(admins.get(1).name()).isEqualTo("asdasd");
        Assertions.assertThat(admins.get(1).role()).isEqualTo(Role.ADMIN);
        Assertions.assertThat(admins.get(2).account()).isEqualTo("zxcvb1234");
        Assertions.assertThat(admins.get(2).name()).isEqualTo("zxczxc");
        Assertions.assertThat(admins.get(2).role()).isEqualTo(Role.ADMIN);
    }
}

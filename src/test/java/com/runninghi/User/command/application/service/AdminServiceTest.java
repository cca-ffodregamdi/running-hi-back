package com.runninghi.User.command.application.service;


import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
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
        for (UserInfoResponse user : users) {
            if (user.account().equals("qwerty1234")) {
                Assertions.assertThat(user.account()).isEqualTo("qwerty1234");
                Assertions.assertThat(user.name()).isEqualTo("qweqwe");
            }
            if (user.account().equals("asdfg1234")) {
                Assertions.assertThat(user.account()).isEqualTo("asdfg1234");
                Assertions.assertThat(user.name()).isEqualTo("asdasd");
            }
            if (user.account().equals("zxcvb1234")) {
                Assertions.assertThat(user.account()).isEqualTo("zxcvb1234");
                Assertions.assertThat(user.name()).isEqualTo("zxczxc");
            }
            Assertions.assertThat(user.role()).isEqualTo(Role.USER);
        }
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
        for (UserInfoResponse admin : admins) {
            if (admin.account().equals("qwerty1234")) {
                Assertions.assertThat(admin.account()).isEqualTo("qwerty1234");
                Assertions.assertThat(admin.name()).isEqualTo("qweqwe");
            }
            if (admin.account().equals("asdfg1234")) {
                Assertions.assertThat(admin.account()).isEqualTo("asdfg1234");
                Assertions.assertThat(admin.name()).isEqualTo("asdasd");
            }
            if (admin.account().equals("zxcvb1234")) {
                Assertions.assertThat(admin.account()).isEqualTo("zxcvb1234");
                Assertions.assertThat(admin.name()).isEqualTo("zxczxc");
            }
            Assertions.assertThat(admin.role()).isEqualTo(Role.ADMIN);
        }
    }
}

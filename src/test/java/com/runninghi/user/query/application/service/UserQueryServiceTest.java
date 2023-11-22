package com.runninghi.user.query.application.service;

import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
import com.runninghi.user.query.application.dto.user.response.UserInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserQueryServiceTest {
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private UserCommandRepository userCommandRepository;
    @Autowired
    private PasswordEncoder encoder;

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
}

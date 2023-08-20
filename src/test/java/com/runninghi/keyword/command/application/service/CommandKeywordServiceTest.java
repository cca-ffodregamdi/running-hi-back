package com.runninghi.keyword.command.application.service;

import com.runninghi.keyword.command.application.dto.response.KeywordCreateResponse;
import com.runninghi.keyword.command.domain.repository.CommandKeywordRepository;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.assertj.core.api.Assertions;

@SpringBootTest
@Transactional
class CommandKeywordServiceTest {

    @Autowired
    private CommandKeywordService commandKeywordService;
    @Autowired
    private CommandKeywordRepository commandKeywordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }

    @DisplayName("키워드 생성 테스트 : 작성자가 관리자가 맞는 지 확인")
    @Test
    void testCheckAdminByAdminUserKey() {

        // given
        User admin = userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("qweqwe")
                .role(Role.ADMIN)
                .build());

        // when & then
        Assertions.assertThatCode(
                () -> commandKeywordService.checkAdminByUserKey(admin.getId())

                ).doesNotThrowAnyException();
    }

    @DisplayName("키워드 생성 테스트 : 일반 유저가 생성 시도 시 예외처리 확인")
    @Test
    void testCheckAdminByUserUserKey() {

        // given
        User admin = userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("qweqwe")
                .role(Role.USER)
                .build());

        // when & then
        Assertions.assertThatThrownBy(() -> commandKeywordService.checkAdminByUserKey(admin.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("관리자만 생성이 가능합니다.");
    }


    @DisplayName("키워드 생성 테스트 : success")
    @Test
    void createKeyword() {

        // given
        Long beforeSize = commandKeywordRepository.count();

        // when
        KeywordCreateResponse  insertedKeyword = commandKeywordService.createKeyword("낮과 밤");
        Long afterSize = commandKeywordRepository.count();

        // then
        Assertions.assertThat(afterSize)
                .isEqualTo(beforeSize + 1);
    }
}
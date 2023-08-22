package com.runninghi.keyword.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.keyword.command.application.dto.request.KeywordUpdateRequest;
import com.runninghi.keyword.command.application.dto.response.KeywordCreateResponse;
import com.runninghi.keyword.command.application.dto.response.KeywordUpdateResponse;
import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.command.domain.repository.KeywordCommandRepository;
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
class KeywordCommandServiceTest {

    @Autowired
    private KeywordCommandService keywordCommandService;
    @Autowired
    private KeywordCommandRepository keywordCommandRepository;
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
                () -> keywordCommandService.checkAdminByUserKey(admin.getId())
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
        Assertions.assertThatThrownBy(() -> keywordCommandService.checkAdminByUserKey(admin.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("관리자만 생성이 가능합니다.");
    }


    @DisplayName("키워드 생성 테스트 : success")
    @Test
    void createKeyword() {

        // given
        Long beforeSize = keywordCommandRepository.count();

        // when
        KeywordCreateResponse  insertedKeyword = keywordCommandService.createKeyword("낮과 밤");
        Long afterSize = keywordCommandRepository.count();

        // then
        Assertions.assertThat(afterSize)
                .isEqualTo(beforeSize + 1);
    }

    @DisplayName("키워드 수정 테스트 : success")
    @Test
    void testUpdateKeyword() {

        // given
        keywordCommandRepository.save(Keyword.builder()
                .keywordNo(1L)
                .keywordName("test1")
                .build()
        );

        KeywordUpdateRequest request = KeywordUpdateRequest.from(
                Keyword.builder()
                        .keywordNo(1L)
                        .keywordName("테스트1")
                        .build()
        );

        // when
        KeywordUpdateResponse result = keywordCommandService.updateKeyword(request);

        // when & then
        Assertions.assertThat(result.keywordName())
                .isEqualTo("테스트1");
    }

    @DisplayName("키워드 수정 테스트 : 수정하려는 키워드가 존재하지 않을 때 Not Found 예외 처리 확인")
    @Test
    void testKeywordUpdateNotFound() {

        // given
        keywordCommandRepository.deleteAllInBatch();
        KeywordUpdateRequest request = KeywordUpdateRequest.from(
                Keyword.builder()
                        .keywordNo(1L)
                        .keywordName("이이")
                        .build()
        );

        // when & then
        Assertions.assertThatThrownBy(
                () -> keywordCommandService.updateKeyword(request)
        )
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 키워드입니다.");

    }
}
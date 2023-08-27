package com.runninghi.keyword.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.keyword.command.application.dto.request.KeywordUpdateRequest;
import com.runninghi.keyword.command.application.dto.response.KeywordResponse;
import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.command.domain.repository.KeywordCommandRepository;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class KeywordCommandServiceTest {

    @Autowired
    private KeywordCommandService keywordCommandService;
    @Autowired
    private KeywordCommandRepository keywordCommandRepository;
    @Autowired
    private UserCommandRepository userCommandRepository;
    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void clear() {
        userCommandRepository.deleteAllInBatch();
    }

    @DisplayName("키워드 생성 테스트 : 작성자가 관리자가 맞는 지 확인")
    @Test
    void testCheckAdminByAdminUserKey() {

        // given
        User admin = userCommandRepository.save(User.builder()
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
        User admin = userCommandRepository.save(User.builder()
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
        KeywordResponse insertedKeyword = keywordCommandService.createKeyword("낮과 밤");
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
        KeywordResponse result = keywordCommandService.updateKeyword(request);

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
                .hasMessage("일치하는 키워드가 없습니다.");

    }

    @DisplayName("키워드 삭제 테스트 : success")
    @Test
    void testDeleteKeywordSuccess() {

        // given
        KeywordResponse testCreated = keywordCommandService.createKeyword("테테스스트트");

        // when
        int response = keywordCommandService.deleteKeyword(testCreated.keywordNo());

        // then
        Assertions.assertThat(response)
                .isEqualTo(1);
    }

    @DisplayName("키워드 삭제 테스트 : 키워드가 존재하지 않을 때 Not Found 예외 처리 되는 지 확인")
    @Test
    void testDeleteKeywordNotFound() {

        // given
        keywordCommandRepository.deleteAllInBatch();

        // when & then
        Assertions.assertThatThrownBy(
                        () -> keywordCommandService.deleteKeyword(1L)
                )
                .isInstanceOf(NotFoundException.class)
                .hasMessage("일치하는 키워드가 없습니다.");
    }
}
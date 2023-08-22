package com.runninghi.keyword.query.application.service;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.command.domain.repository.KeywordCommandRepository;
import com.runninghi.keyword.query.application.dto.response.FindKeywordResponse;
import com.runninghi.keyword.query.infrastructure.repository.KeywordQueryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class KeywordQueryServiceTest {

    @Autowired
    private KeywordCommandRepository keywordCommandRepository;
    @Autowired
    private KeywordQueryService keywordQueryService;
    @Autowired
    private KeywordQueryRepository keywordQueryRepository;

    @AfterEach
    void clear() {
        keywordCommandRepository.deleteAllInBatch();
    }

    @DisplayName("키워드 조회 테스트 : success")
    @Test
    void testFindKeyword () {

        // given
        keywordQueryRepository.save(Keyword.builder()
                .keywordNo(1L)
                .keywordName("test1")
                .build()
        );
        String keywordName = "test1";

        // when
        FindKeywordResponse keyword = keywordQueryService.findKeyword(keywordName);

        // then
        Assertions.assertThat(keyword.keywordName())
                .isEqualTo(keywordName);
    }

    @DisplayName("키워드 조회 테스트 : 검색 결과가 없을 시 Not Found 발생 확인")
    @Test
    void testFindKeywordNotFound () {

        // given
        String keywordName = "asdfbo";

        // when & then
        Assertions.assertThatThrownBy(
                () -> keywordQueryService.findKeyword(keywordName)
        ).hasMessage("일치하는 키워드가 없습니다.");
    }

    @DisplayName("키워드 전체 리스트 조회 테스트 : success")
    @Test
    void testFindKeywordList () {

        // given
        keywordQueryRepository.save(Keyword.builder()
                .keywordNo(1L)
                .keywordName("test1")
                .build()
        );
        keywordQueryRepository.save(Keyword.builder()
                .keywordNo(2L)
                .keywordName("test2")
                .build()
        );

        // when
        List<FindKeywordResponse> keywordList = keywordQueryService.findKeywordList();

        // then
        Assertions.assertThat(keywordList)
                .hasSizeGreaterThan(1);
    }

    @DisplayName("키워드 전체 리스트 조회 테스트 : 조회 결과가 null일 시 예외 처리")
    @Test
    void testFindKeywordListFail () {

        // given
        keywordCommandRepository.deleteAll();

        // when & then
        Assertions.assertThatThrownBy(() -> keywordQueryService.findKeywordList())
                .isInstanceOf(NullPointerException.class);
    }


}
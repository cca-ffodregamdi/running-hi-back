package com.runninghi.keyword.query.application.service;

import com.runninghi.keyword.command.domain.repository.KeywordCommandRepository;
import com.runninghi.keyword.query.application.dto.response.GetKeywordListResponse;
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

//    @BeforeEach
//    void example() {
//        Keyword keyword1 = new Keyword(1L, "테스트1");
//        Keyword keyword2 = new Keyword(2L,"테스트2");
//
//        commandKeywordRepository.save(keyword1);
//        commandKeywordRepository.save(keyword2);
//    }
//
//    @AfterEach
//    void clear() {
//        commandKeywordRepository.deleteAll();
//    }

    @DisplayName("키워드 전체 리스트 조회 테스트 : success")
    @Test
    void testGetKeywordList () {

        // when
        List<GetKeywordListResponse> keywordList = keywordQueryService.getKeywordList();

        // then
        Assertions.assertThat(keywordList)
                .hasSizeGreaterThan(1);
    }

    @DisplayName("키워드 전체 리스트 조회 테스트 : 조회 결과가 null일 시 예외 처리")
    @Test
    void testGetKeywordListFail () {

        // given
        keywordCommandRepository.deleteAll();

        // when & then
        Assertions.assertThatThrownBy(() -> keywordQueryService.getKeywordList())
                .isInstanceOf(NullPointerException.class);
    }
}
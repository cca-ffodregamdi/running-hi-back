package com.runninghi.keyword.query.application.service;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.command.domain.repository.KeywordRepository;
import com.runninghi.keyword.query.application.dto.GetKeywordListResponse;
import com.runninghi.keyword.query.infrastructure.repository.QueryKeywordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class QueryKeywordServiceTest {

    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private QueryKeywordService queryKeywordService;
    @Autowired
    private QueryKeywordRepository queryKeywordRepository;

    @BeforeEach
    void example() {
        Keyword keyword1 = new Keyword(1L, "테스트1");
        Keyword keyword2 = new Keyword(2L,"테스트2");

        keywordRepository.save(keyword1);
        keywordRepository.save(keyword2);
    }

    @AfterEach
    void clear() {
        keywordRepository.deleteAll();
    }

    @DisplayName("키워드 전체 리스트 조회 : success")
    @Test
    void testGetKeywordList () {

        // when
        List<GetKeywordListResponse> keywordList = queryKeywordService.getKeywordList();

        // then
        Assertions.assertThat(keywordList)
                .hasSize(2);
    }
}
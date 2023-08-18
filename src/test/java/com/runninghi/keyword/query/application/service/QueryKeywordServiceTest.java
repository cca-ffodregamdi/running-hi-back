package com.runninghi.keyword.query.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QueryKeywordServiceTest {

    @Autowired
    private QueryKeywordService queryKeywordService;

    @DisplayName("키워드 전체 리스트 조회 : success")
    @Test
    void testGetKeywordList () {

        // given
    }
}
package com.runninghi.keyword.command.application.service;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.command.domain.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRespository;

    @Transactional
    public void createKeyword(String keywordName) {
        Keyword result = keywordRespository.save(Keyword.builder()
                                        .keywordName(keywordName)
                                        .build());
    }
}

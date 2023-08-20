package com.runninghi.keyword.command.application.service;

import com.runninghi.keyword.command.application.dto.response.UserCheckResponse;
import com.runninghi.keyword.command.application.dto.request.KeywordCreateRequest;
import com.runninghi.keyword.command.application.dto.response.KeywordCreateResponse;
import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.command.domain.repository.KeywordRepository;
import com.runninghi.keyword.command.domain.service.ApiKeywordDomainService;
import com.runninghi.keyword.command.domain.service.KeywrodDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywrodDomainService keywordDomainService;
    private final ApiKeywordDomainService apiKeywordDomainService;
    private final KeywordRepository keywordRepository;

    public UserCheckResponse checkAdminByUserKey(UUID userKey) {
        UserCheckResponse apiResult = apiKeywordDomainService.checkUserByUserKey(userKey);
        keywordDomainService.checkAdmin(apiResult);
        return apiResult;
    }

    @Transactional
    public KeywordCreateResponse createKeyword(KeywordCreateRequest request) {
        Keyword result = keywordRepository.save(Keyword.builder()
                .keywordName(request.keywordName())
                .build());
        return KeywordCreateResponse.of(result.getKeywordNo(), result.getKeywordName());
    }
}

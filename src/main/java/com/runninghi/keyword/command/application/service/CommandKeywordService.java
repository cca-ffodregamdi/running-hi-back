package com.runninghi.keyword.command.application.service;

import com.runninghi.keyword.command.application.dto.response.UserCheckResponse;
import com.runninghi.keyword.command.application.dto.response.KeywordCreateResponse;
import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.command.domain.repository.CommandKeywordRepository;
import com.runninghi.keyword.command.domain.service.ApiKeywordDomainService;
import com.runninghi.keyword.command.domain.service.CommandKeywrodDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommandKeywordService {

    private final CommandKeywrodDomainService keywordDomainService;
    private final ApiKeywordDomainService apiKeywordDomainService;
    private final CommandKeywordRepository commandKeywordRepository;

    public UserCheckResponse checkAdminByUserKey(UUID userKey) {
        UserCheckResponse apiResult = apiKeywordDomainService.checkUserByUserKey(userKey);
        keywordDomainService.checkAdmin(apiResult);
        return apiResult;
    }

    @Transactional
    public KeywordCreateResponse createKeyword(String keywordName) {
        Keyword result = commandKeywordRepository.save(Keyword.builder()
                .keywordName(keywordName)
                .build());
        return KeywordCreateResponse.of(result.getKeywordNo(), result.getKeywordName());
    }
}

package com.runninghi.keyword.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.keyword.command.application.dto.request.KeywordUpdateRequest;
import com.runninghi.keyword.command.application.dto.response.UserCheckResponse;
import com.runninghi.keyword.command.application.dto.response.KeywordCreateResponse;
import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.command.domain.repository.KeywordCommandRepository;
import com.runninghi.keyword.command.domain.service.ApiKeywordDomainService;
import com.runninghi.keyword.command.domain.service.KeywordCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeywordCommandService {

    private final KeywordCommandDomainService keywordDomainService;
    private final ApiKeywordDomainService apiKeywordDomainService;
    private final KeywordCommandRepository keywordCommandRepository;

    public UserCheckResponse checkAdminByUserKey(UUID userKey) {
        UserCheckResponse apiResult = apiKeywordDomainService.checkUserByUserKey(userKey);
        keywordDomainService.checkAdmin(apiResult);
        return apiResult;
    }

    @Transactional
    public KeywordCreateResponse createKeyword(String keywordName) {
        Keyword result = keywordCommandRepository.save(Keyword.builder()
                .keywordName(keywordName)
                .build());
        return KeywordCreateResponse.of(result.getKeywordNo(), result.getKeywordName());
    }

    @Transactional
    public Keyword updateKeyword(KeywordUpdateRequest request) {
        Keyword keyword = keywordCommandRepository.findById(request.keywordNo())
                .orElseThrow( () -> new NotFoundException("존재하지 않는 키워드입니다."));
        keyword.update(keyword);
        System.out.println(keyword.toString());
        return keyword;
    }

}

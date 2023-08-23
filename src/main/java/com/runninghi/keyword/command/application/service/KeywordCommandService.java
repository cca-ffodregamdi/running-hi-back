package com.runninghi.keyword.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.keyword.command.application.dto.request.KeywordUpdateRequest;
import com.runninghi.keyword.command.application.dto.response.UserCheckResponse;
import com.runninghi.keyword.command.application.dto.response.KeywordResponse;
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
    public KeywordResponse createKeyword(String keywordName) {
        Keyword result = keywordCommandRepository.save(Keyword.builder()
                .keywordName(keywordName)
                .build());
        return KeywordResponse.of(result.getKeywordNo(), result.getKeywordName());
    }

    @Transactional
    public KeywordResponse updateKeyword(KeywordUpdateRequest request) {
        Keyword keyword = keywordCommandRepository.findById(request.keywordNo())
                .orElseThrow( () -> new NotFoundException("일치하는 키워드가 없습니다."));
        keyword.update(request.keywordName());
        return KeywordResponse.of(keyword.getKeywordNo(), keyword.getKeywordName());
    }

    @Transactional
    public int deleteKeyword(Long keywordNo) {
        apiKeywordDomainService.findByKeywordNo(keywordNo);
        return keywordCommandRepository.costumDeleteByKeywordNo(keywordNo);
    }

}

package com.runninghi.keyword.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.keyword.command.application.dto.response.KeywordResponse;
import com.runninghi.keyword.command.application.dto.response.UserCheckResponse;
import com.runninghi.keyword.command.domain.service.ApiKeywordDomainService;
import com.runninghi.keyword.query.application.dto.response.FindKeywordResponse;
import com.runninghi.keyword.query.application.service.KeywordQueryService;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import com.runninghi.member.query.application.service.MemberQueryService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class ApiKeywordInfraService implements ApiKeywordDomainService {

    private final KeywordQueryService keywordQueryService;
    private final MemberQueryService userQueryService;

    @Override
    public UserCheckResponse checkUserByUserKey(UUID userKey) {
        MemberInfoResponse result = userQueryService.findMemberInfo(userKey);
        return new UserCheckResponse(
                result.id(),
                result.account(),
                result.name(),
                result.role(),
                result.createdAt()
        );
    }

    @Override
    public KeywordResponse findByKeywordNo(Long keywordNo) {
        FindKeywordResponse response =
                keywordQueryService.findKeywordByKeywordNo(keywordNo);
        return KeywordResponse.of(response.keywordNo(), response.keywordName());
    }
}

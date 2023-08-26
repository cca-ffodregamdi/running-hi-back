package com.runninghi.adminpost.command.infrastructure.service;

import com.runninghi.adminpost.command.application.dto.request.KeywordListRequest;
import com.runninghi.keywordofpost.command.application.dto.request.KeywordOfPostCreateRequest;
import com.runninghi.adminpost.command.domain.service.ApiAdminPostDomainService;
import com.runninghi.common.annotation.InfraService;
import com.runninghi.keywordofpost.command.application.dto.response.KeywordOfAdminPostCreateResponse;
import com.runninghi.keywordofpost.command.application.service.KeywordOfPostCommandService;
import com.runninghi.user.command.application.service.UserService;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@InfraService
@RequiredArgsConstructor
public class ApiAdminPostInfraService implements ApiAdminPostDomainService {

    private final UserService userService;
    private final KeywordOfPostCommandService keywordOfPostCommandService;

    @Override
    public Role checkAdminByUserNo(UUID userKey) {
        return userService.findUserInfo(userKey).role();
    }

    @Override
    @Transactional
    public List<Long> createKeywordOfAdminPost(List<KeywordListRequest> keywordList, Long adminPostNo) {
        List<KeywordOfPostCreateRequest> request = keywordList.stream().map(
                keyword -> KeywordOfPostCreateRequest.adminPostFrom(adminPostNo, keyword.keywordNo(), keyword.keywordName())
        ).collect(Collectors.toList());
        return keywordOfPostCommandService.createKeywordOfAdminPost(request);
    }

}

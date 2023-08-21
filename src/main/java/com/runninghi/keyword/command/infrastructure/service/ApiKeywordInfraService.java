package com.runninghi.keyword.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.keyword.command.application.dto.response.UserCheckResponse;
import com.runninghi.keyword.command.domain.service.ApiKeywordDomainService;
import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.command.application.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class ApiKeywordInfraService implements ApiKeywordDomainService {

    private final UserService userService;

    @Override
    public UserCheckResponse checkUserByUserKey(UUID userKey) {
        UserInfoResponse result = userService.getUserInfo(userKey);
        return new UserCheckResponse(
                result.id(),
                result.account(),
                result.name(),
                result.role(),
                result.createdAt()
        );
    }
}

package com.runninghi.keyword.command.domain.service;


import com.runninghi.keyword.command.application.dto.response.UserCheckResponse;

import java.util.UUID;

public interface ApiKeywordDomainService {

     UserCheckResponse checkUserByUserKey(UUID userKey);
}

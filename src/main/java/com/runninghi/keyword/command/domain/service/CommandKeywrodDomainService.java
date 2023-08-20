package com.runninghi.keyword.command.domain.service;

import com.runninghi.common.annotation.DomainService;
import com.runninghi.keyword.command.application.dto.response.UserCheckResponse;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;

@DomainService
public class CommandKeywrodDomainService {
    public void checkAdmin(UserCheckResponse apiResult) {
        int checkAdmin = apiResult.role().compareTo(Role.ADMIN);
        if (checkAdmin != 0) {
            throw new IllegalArgumentException("관리자만 생성이 가능합니다.");
        }
    }
}

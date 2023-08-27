package com.runninghi.adminpost.command.domain.service;

import com.runninghi.adminpost.command.application.dto.request.AdminPostCreateRequest;
import com.runninghi.common.annotation.DomainService;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;

@DomainService
public class AdminPostCommandDomainService {
    public void checkAdmin(Role apiResult) {    // 추후에 User 도메인으로 이동 예정
        int checkAdmin = apiResult.compareTo(Role.ADMIN);
        if (checkAdmin != 0) {
            throw new IllegalArgumentException("관리자만 생성이 가능합니다.");
        }
    }
}

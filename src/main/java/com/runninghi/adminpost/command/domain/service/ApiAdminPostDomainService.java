package com.runninghi.adminpost.command.domain.service;

import com.runninghi.adminpost.command.application.dto.request.KeywordListRequest;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;

import java.util.List;
import java.util.UUID;

public interface ApiAdminPostDomainService {
    Role checkAdminByUserNo(UUID userKey);

    List<Long> createKeywordOfAdminPost(List<KeywordListRequest> keywordNoList, Long adminPostNo);
}

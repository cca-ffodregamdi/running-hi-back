package com.runninghi.adminpost.command.domain.service;

import com.runninghi.adminpost.command.application.dto.request.KeywordListRequest;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ApiAdminPostDomainService {
     Role checkAdminByUserNo(UUID userKey);
     List<Long> createKeywordOfAdminPost(List<KeywordListRequest> keywordNoList, Long adminPostNo);

    List<Long> updateKeywordOfAdminPost(List<KeywordListRequest> keywordListRequests, Long adminPostNo);

    String uploadThumbnail(MultipartFile thumbnail);

    void deleteOldThumbnail(String adminPostThumbnailUrl);
}

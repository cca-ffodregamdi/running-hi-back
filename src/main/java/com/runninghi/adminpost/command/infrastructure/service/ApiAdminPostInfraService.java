package com.runninghi.adminpost.command.infrastructure.service;

import com.runninghi.adminpost.command.application.dto.request.KeywordListRequest;
import com.runninghi.image.command.application.dto.request.ImageDeleteRequest;
import com.runninghi.image.command.application.service.ImageCommandService;
import com.runninghi.keywordofpost.command.application.dto.request.KeywordOfPostCreateRequest;
import com.runninghi.adminpost.command.domain.service.ApiAdminPostDomainService;
import com.runninghi.common.annotation.InfraService;
import com.runninghi.keywordofpost.command.application.service.KeywordOfPostCommandService;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@InfraService
@RequiredArgsConstructor
public class ApiAdminPostInfraService implements ApiAdminPostDomainService {

    private final UserQueryService userQueryService;
    private final KeywordOfPostCommandService keywordOfPostCommandService;
    private final ImageCommandService imageCommandService;

    @Override
    public Role checkAdminByUserNo(UUID userKey) {
        return userQueryService.findUserInfo(userKey).role();
    }

    @Override
    public List<Long> createKeywordOfAdminPost(List<KeywordListRequest> keywordList, Long adminPostNo) {
        List<KeywordOfPostCreateRequest> request = keywordList.stream().map(
                keyword -> KeywordOfPostCreateRequest.adminPostFrom(adminPostNo, keyword.keywordNo(), keyword.keywordName())
        ).collect(Collectors.toList());
        return keywordOfPostCommandService.createKeywordOfAdminPost(request);
    }

    @Override
    public List<Long> updateKeywordOfAdminPost(List<KeywordListRequest> keywordList, Long adminPostNo) {
        List<KeywordOfPostCreateRequest> request = keywordList.stream().map(
                keyword -> KeywordOfPostCreateRequest.adminPostFrom(adminPostNo, keyword.keywordNo(), keyword.keywordName() )
        ).toList();
        return keywordOfPostCommandService.updateKeywordOfAdminPost(request);
    }

    @Override
    public String uploadThumbnail(MultipartFile thumbnail) {
         return imageCommandService.uploadImageFile(thumbnail, "admin").imageUrl();
    }

    @Override
    public void deleteOldThumbnail(String adminPostThumbnailUrl) {
        ImageDeleteRequest imageDeleteRequest = new ImageDeleteRequest(adminPostThumbnailUrl, "admin");
        imageCommandService.deleteImageFile(imageDeleteRequest);
    }

    @Override
    public void deleteKeywordOfPost(Long adminPostNo) {
        keywordOfPostCommandService.deleteKeywordOfAdminPost(adminPostNo);
    }
}

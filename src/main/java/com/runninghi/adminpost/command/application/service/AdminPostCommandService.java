package com.runninghi.adminpost.command.application.service;

import com.runninghi.adminpost.command.application.dto.request.AdminPostRequest;
import com.runninghi.adminpost.command.application.dto.response.AdminPostResponse;
import com.runninghi.adminpost.command.domain.aggregate.entity.AdminPost;
import com.runninghi.adminpost.command.domain.aggregate.vo.WriterNoVO;
import com.runninghi.adminpost.command.domain.repository.AdminPostCommandRepository;
import com.runninghi.adminpost.command.domain.service.AdminPostCommandDomainService;
import com.runninghi.adminpost.command.domain.service.ApiAdminPostDomainService;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminPostCommandService {

    private final AdminPostCommandDomainService adminPostCommandDomainService;
    private final ApiAdminPostDomainService apiAdminPostDomainService;
    private final AdminPostCommandRepository adminPostCommandRepository;

    public void checkAdminByUserNo(UUID userKey) {
        Role apiResult = apiAdminPostDomainService.checkAdminByUserNo(userKey);
        adminPostCommandDomainService.checkAdmin(apiResult);
    }

    @Transactional
    public AdminPostResponse createAdminPost(AdminPostRequest request) {

        checkAdminByUserNo(request.userKey());

        AdminPost result = adminPostCommandRepository.save(
                AdminPost.builder()
                        .writerNoVO(new WriterNoVO(request.userKey()))
                        .adminPostTitle(request.adminPostTitle())
                        .adminPostContent(request.adminPostContent())
                        .adminPostThumbnail(request.thumbnail())
                        .build()
        );

        List<Long> keywordNoList = apiAdminPostDomainService.createKeywordOfAdminPost(
                request.keywordList(),
                result.getAdminPostNo()
        );

        return AdminPostResponse.of(
                result.getWriterNoVO().getWriterNo(),
                result.getAdminPostThumbnail(),
                result.getAdminPostTitle(),
                result.getAdminPostContent(),
                keywordNoList
        );
    }

    @Transactional
    public AdminPostResponse updateAdminPost(Long adminPostNo, AdminPostRequest request) {

        checkAdminByUserNo(request.userKey());

        AdminPost result = adminPostCommandRepository.save(
                AdminPost.builder()
                        .adminPostNo(adminPostNo)
                        .writerNoVO(new WriterNoVO(request.userKey()))
                        .adminPostTitle(request.adminPostTitle())
                        .adminPostContent(request.adminPostContent())
                        .adminPostThumbnail(request.thumbnail())
                        .build()
        );

        List<Long> keywordList = apiAdminPostDomainService.updateKeywordOfAdminPost(
                request.keywordList(),
                result.getAdminPostNo()
        );

        return AdminPostResponse.of(
                result.getWriterNoVO().getWriterNo(),
                result.getAdminPostThumbnail(),
                result.getAdminPostTitle(),
                result.getAdminPostContent(),
                keywordList
        );
    }
}

package com.runninghi.userpost.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.keywordofpost.command.application.dto.request.KeywordOfPostCreateRequest;
import com.runninghi.keywordofpost.command.application.service.KeywordOfPostCommandService;
import com.runninghi.keywordofpost.command.domain.aggregate.entity.KeywordOfPost;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.AdminPostVO;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.KeywordVO;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.UserPostVO;
import com.runninghi.keywordofpost.command.domain.repository.KeywordOfPostCommandRepository;
import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.query.application.service.UserQueryService;
import com.runninghi.userpost.command.application.dto.request.KeywordListRequest;
import com.runninghi.userpost.command.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.command.domain.service.ApiUserPostCommandDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@InfraService
@RequiredArgsConstructor
public class ApiUserPostCommandInfraService implements ApiUserPostCommandDomainService {

private final UserQueryService userQueryService;
private final KeywordOfPostCommandService keywordOfPostCommandService;
private final KeywordOfPostCommandRepository keywordOfPostCommandRepository;

    @Transactional
    public UserPostUserResponse checkUser(UUID userId) {

        UserInfoResponse result = userQueryService.findUserInfo(userId);

        return new UserPostUserResponse(
                result.id(),
                result.account(),
                result.name(),
                result.nickname(),
                result.email(),
                result.kakaoId(),
                result.kakaoName(),
                result.reportCount(),
                result.blackListStatus(),
                result.status(),
                result.role(),
                result.createdAt()
        );
    }

    @Transactional
    public List<Long> createKeywordOfUserPost(List<KeywordListRequest> keywordList, Long userPostNo) {
        List<KeywordOfPostCreateRequest> request = keywordList.stream().map(
                keyword -> KeywordOfPostCreateRequest.userPostFrom(userPostNo, keyword.keywordNo(), keyword.keywordName())
        ).collect(Collectors.toList());
        return createKeywordOfUserPost(request);
    }

    private List<Long> createKeywordOfUserPost(List<KeywordOfPostCreateRequest> requests) {

        List<KeywordOfPost> keywordOfPostList = requests.stream().map(
                request -> KeywordOfPost.builder()
                        .adminPostVO(new AdminPostVO(request.adminPostNo()))
                        .userPostVO(new UserPostVO(request.userPostNo()))
                        .keywordVO(new KeywordVO(request.keywordNo(), request.keywordName()))
                        .build()
        ).collect(Collectors.toList());

        try {
            return keywordOfPostCommandRepository.saveAll(keywordOfPostList)
                    .stream().map( k -> k.getKeywordVO().getKeywordNo()
                    ).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("저장에 실패하였습니다.");
        }
    }

    @Transactional
    public List<Long> updateKeywordOfUserPost(List<KeywordListRequest> keywordList, Long userPostNo) {
        // keywordOfPostCommandRepository.deleteAllBy
        List<KeywordOfPostCreateRequest> request = keywordList.stream().map(
                keyword -> KeywordOfPostCreateRequest.userPostFrom(userPostNo, keyword.keywordNo(), keyword.keywordName())
        ).collect(Collectors.toList());
        return createKeywordOfUserPost(request);
    }

}

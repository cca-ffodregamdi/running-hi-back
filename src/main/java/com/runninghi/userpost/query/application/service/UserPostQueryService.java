package com.runninghi.userpost.query.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.common.handler.feedback.customException.NotMatchWriterException;
import com.runninghi.userpost.command.domain.aggregate.entity.UserPost;
import com.runninghi.userpost.query.application.dto.request.GetUserPostRequest;
import com.runninghi.userpost.query.application.dto.response.GetUserPostResponse;
import com.runninghi.userpost.query.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.query.domain.repository.UserPostQueryRepository;
import com.runninghi.userpost.query.domain.service.UserPostQueryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPostQueryService {

    private final UserPostQueryRepository userPostQueryRepository;
    private final UserPostQueryDomainService userPostQueryDomainService;

    @Transactional(readOnly = true)
    public GetUserPostResponse getUserPost(GetUserPostRequest request, UserPostUserResponse user, Long postNo) {
        // postNo으로 User Post 조회
        UserPost userPost = userPostQueryRepository.findById(postNo)
                .orElseThrow(() -> new NotFoundException("일치하는 게시물이 없습니다."));

        // 본인이 작성한 글인지 확인
        if (!userPostQueryDomainService.isWriter(user, userPost)) {
            throw new NotMatchWriterException("작성자가 일치하지않습니다.");
        }

        // postNo으로 관련된 Image List 조회

        // postNo으로 관련된 댓글 조회

        return null;
    }
}

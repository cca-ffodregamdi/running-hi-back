package com.runninghi.userpost.query.application.service;

import com.runninghi.userpost.query.application.dto.request.GetUserPostRequest;
import com.runninghi.userpost.query.application.dto.response.GetUserPostResponse;
import com.runninghi.userpost.query.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.query.domain.repository.UserPostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPostQueryService {

    private final UserPostQueryRepository userPostQueryRepository;

    @Transactional(readOnly = true)
    public GetUserPostResponse getUserPost(GetUserPostRequest request, UserPostUserResponse user) {
        // postNo으로 User Post 조회

        // 본인이 작성한 글인지 확인

        // postNo으로 관련된 Image List 조회

        // postNo으로 관련된 댓글 조회

        return null;
    }
}

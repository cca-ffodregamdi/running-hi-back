package com.runninghi.userpost.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.userpost.command.application.dto.request.UserPostCreateRequest;
import com.runninghi.userpost.command.application.dto.request.UserPostUpdateRequest;
import com.runninghi.userpost.command.application.dto.response.UserPostResponse;
import com.runninghi.userpost.command.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.command.domain.aggregate.entity.UserPost;
import com.runninghi.userpost.command.domain.aggregate.vo.UserVO;
import com.runninghi.userpost.command.domain.repository.UserPostCommandRepository;
import com.runninghi.userpost.command.domain.service.UserPostCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserPostCommandService {

    private final UserPostCommandRepository userPostCommandRepository;

    private final UserPostCommandDomainService userPostCommandDomainService;

    // 유저 게시물 생성
    // 관련된 keyword Of post 생성
    public UserPostResponse createUserPost(UserPostCreateRequest userPostCreateRequest, UserPostUserResponse user) {

        // 유저 게시물 제한 사항 확인
        userPostCommandDomainService.checkUserPostValidation(userPostCreateRequest.userPostTitle());

        // 작성자 vo 작성
        UserVO userVO = new UserVO(user.id());

        UserPost userPost = new UserPost.Builder()
                .userPostTitle(userPostCreateRequest.userPostTitle())
                .userPostContent(userPostCreateRequest.userPostContent())
                .userPostDate(new Date())
                .userVO(userVO)
                .build();

        // 유저 게시물 저장
        UserPost createdUserPost = userPostCommandRepository.save(userPost);

        return UserPostResponse.from(createdUserPost);

    }

    // 유저 게시물 수정
    // 관련된 keyword Of post 수정
    public UserPostResponse updateUserPost(UserPostUpdateRequest userPostUpdateRequest, UserPostUserResponse user) {

        // 유저 게시물 제한 사항 확안
        userPostCommandDomainService.checkUserPostValidation(userPostUpdateRequest.userPostTitle());

        // 유저 게시물이 존재하는지 확인
        UserPost userPost = userPostCommandRepository.findByUserPostNo(userPostUpdateRequest.userPostNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 게시물입니다."));

        // 요청자와 게시물 작성자가 일치하는지 확인
        userPostCommandDomainService.isWriter(user, userPost);

        UserPost updateUserPost = new UserPost.Builder()
                .userPostNo(userPost.getUserPostNo())
                .userPostTitle(userPostUpdateRequest.userPostTitle())
                .userPostContent(userPostUpdateRequest.userPostContent())
                .userPostModifiedDate(new Date())
                .userPostDate(userPost.getUserPostDate())
                .userVO(userPost.getUserVO())
                .build();

        UserPost result = userPostCommandRepository.save(updateUserPost);

        return UserPostResponse.from(result);
    }

    // 유저 게시물 삭제
    // 관련된 keyword Of post 삭제
    // 관련된 이미지 삭제
    // 관련된 댓글 삭제
    // 관련된 즐겨찾기 삭제

}

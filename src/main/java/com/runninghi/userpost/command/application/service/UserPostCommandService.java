package com.runninghi.userpost.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.common.handler.feedback.customException.NotMatchWriterException;
import com.runninghi.userpost.command.application.dto.request.UserPostCreateRequest;
import com.runninghi.userpost.command.application.dto.request.UserPostDeleteRequest;
import com.runninghi.userpost.command.application.dto.request.UserPostUpdateRequest;
import com.runninghi.userpost.command.application.dto.response.UserPostResponse;
import com.runninghi.userpost.command.application.dto.response.UserPostUserResponse;
import com.runninghi.userpost.command.domain.aggregate.entity.UserPost;
import com.runninghi.userpost.command.domain.aggregate.vo.UserVO;
import com.runninghi.userpost.command.domain.repository.UserPostCommandRepository;
import com.runninghi.userpost.command.domain.service.UserPostCommandDomainService;
import com.runninghi.userpost.command.infrastructure.service.ApiUserPostCommandInfraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPostCommandService {

    private final UserPostCommandRepository userPostCommandRepository;
    private final UserPostCommandDomainService userPostCommandDomainService;
    private final ApiUserPostCommandInfraService apiUserPostCommandInfraService;


    // 유저 게시물 생성
    @Transactional
    public UserPostResponse createUserPost(UserPostCreateRequest request, UserPostUserResponse user) {

        // 유저 게시물 제한 사항 확인
        userPostCommandDomainService.checkUserPostValidation(request.userPostTitle());

        // 작성자 vo 작성
        UserVO userVO = new UserVO(user.id());

        UserPost userPost = new UserPost.Builder()
                .userPostTitle(request.userPostTitle())
                .userPostContent(request.userPostContent())
                .userPostDate(new Date())
                .userVO(userVO)
                .build();

        // 유저 게시물 저장
        UserPost createdUserPost = userPostCommandRepository.save(userPost);

        // 관련된 keyword Of post 생성
        List<Long> keywordNoList = apiUserPostCommandInfraService.createKeywordOfUserPost(
                request.keywordList(),
                createdUserPost.getUserPostNo()
        );

        return UserPostResponse.from(createdUserPost);

    }

    // 유저 게시물 수정
    @Transactional
    public UserPostResponse updateUserPost(UserPostUpdateRequest request, UserPostUserResponse user) {

        // 유저 게시물 제한 사항 확안
        userPostCommandDomainService.checkUserPostValidation(request.userPostTitle());

        // 유저 게시물이 존재하는지 확인

        UserPost userPost = userPostCommandRepository.findByUserPostNo(request.userPostNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 게시물입니다."));

        // 요청자와 게시물 작성자가 일치하는지 확인
        userPostCommandDomainService.isWriter(user, userPost);

        UserPost updateUserPost = new UserPost.Builder()
                .userPostNo(userPost.getUserPostNo())
                .userPostTitle(request.userPostTitle())
                .userPostContent(request.userPostContent())
                .userPostModifiedDate(new Date())
                .userPostDate(userPost.getUserPostDate())
                .userVO(userPost.getUserVO())
                .build();

        UserPost result = userPostCommandRepository.save(updateUserPost);

        // 관련된 keyword Of post 수정

        return UserPostResponse.from(result);
    }

    // 유저 게시물 삭제
    @Transactional
    public void deleteUserPost(UserPostDeleteRequest request, UserPostUserResponse user) {

        UserPost userPost = userPostCommandRepository.findByUserPostNo(request.userPostNo())
                .orElseThrow(() -> new NotFoundException("존재하지않는 유저 게시물입니다."));

        // 작성자와 현재 회원이 일치하는지 확인
        if (!userPostCommandDomainService.isWriter(user, userPost)) {
            throw new NotMatchWriterException("작성자와 회원이 일치하지않습니다.");
        }

        userPostCommandRepository.delete(userPost);

        // 관련된 keyword Of post 삭제
        // 관련된 이미지 삭제
        // 관련된 댓글 삭제
        // 관련된 즐겨찾기 삭제
        // 관련된 신고 삭제

    }

}

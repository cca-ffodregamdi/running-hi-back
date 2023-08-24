package com.runninghi.user.query.application.service;

import com.runninghi.user.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.user.command.application.dto.user.RefreshTokenDTO;
import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.query.infrastructure.repository.UserQueryRefreshTokenRepository;
import com.runninghi.user.query.infrastructure.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserQueryService {
    private final UserQueryRepository userQueryRepository;
    private final UserQueryRefreshTokenRepository userQueryRefreshTokenRepository;

    // 회원 정보 조회
    @Transactional(readOnly = true)
    public UserInfoResponse findUserInfo(UUID id) {
        return userQueryRepository.findById(id)
                .map(UserInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    // 아이디로 회원 정보 조회
    @Transactional(readOnly = true)
    public UserInfoResponse findUserInfoByAccount(SignInRequest request) {
        return userQueryRepository.findUserByAccount(request.account())
                .map(UserInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    // 리프레쉬 토큰 조회
    @Transactional(readOnly = true)
    public RefreshTokenDTO findRefreshTokenById(User user) {
        return new RefreshTokenDTO(userQueryRefreshTokenRepository.findById(user.getId()));
    }

}

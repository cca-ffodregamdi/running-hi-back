package com.runninghi.user.query.application.service;

import com.runninghi.user.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.user.command.application.dto.user.RefreshTokenDTO;
import com.runninghi.user.command.application.dto.user.request.FindAccountRequest;
import com.runninghi.user.command.application.dto.user.request.FindPasswordRequest;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.query.application.dto.user.response.FindAccountResponse;
import com.runninghi.user.query.application.dto.user.response.FindPasswordResponse;
import com.runninghi.user.query.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.query.infrastructure.repository.UserQueryRefreshTokenRepository;
import com.runninghi.user.query.infrastructure.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserQueryService {
    private final UserQueryRepository userQueryRepository;
    private final UserQueryRefreshTokenRepository userQueryRefreshTokenRepository;

    // 회원 정보 조회
    public UserInfoResponse findUserInfo(UUID id) {
        return userQueryRepository.findById(id)
                .map(UserInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    // 이름 + 이메일로 회원 아이디 조회
    public FindAccountResponse findAccount(FindAccountRequest request) {
        return userQueryRepository.findAccountByNameAndEmail(request.name(), request.email())
                .map(FindAccountResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    // 아이디 + 이메일로 회원 조회
    public FindPasswordResponse findPassword(FindPasswordRequest request) {
        return userQueryRepository.findUserByAccountAndEmail(request.account(), request.email())
                .map(user -> FindPasswordResponse.from(true))
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    // 아이디로 회원 정보 조회
    public UserInfoResponse findUserInfoByAccount(SignInRequest request) {
        return userQueryRepository.findUserByAccount(request.account())
                .map(UserInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    // 리프레쉬 토큰 조회
    public RefreshTokenDTO findRefreshTokenById(User user) {
        return new RefreshTokenDTO(userQueryRefreshTokenRepository.findById(user.getId()));
    }

}

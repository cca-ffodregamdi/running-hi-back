package com.runninghi.user.query.application.service;

import com.runninghi.user.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.user.command.application.dto.user.UserInfoDTO;
import com.runninghi.user.query.infrastructure.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignQueryService {
    private final UserQueryRepository userQueryRepository;

    // 아이디로 회원 정보 조회
    @Transactional(readOnly = true)
    public UserInfoDTO findUserInfoByAccount(SignInRequest request) {
        return new UserInfoDTO(userQueryRepository.findUserByAccount(request.account()));
    }
}

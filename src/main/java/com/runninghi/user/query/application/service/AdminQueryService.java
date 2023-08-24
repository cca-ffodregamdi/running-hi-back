package com.runninghi.user.query.application.service;

import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.query.infrastructure.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminQueryService {
    private final UserQueryRepository userQueryRepository;

    // 전체 유저 정보 조회
    @Transactional(readOnly = true)
    public List<UserInfoResponse> findAllUsers() {
        return userQueryRepository.findAllUserByRole(Role.USER).stream()
                .map(UserInfoResponse::from)
                .toList();
    }

    // 전체 관리자 정보 조회
    @Transactional(readOnly = true)
    public List<UserInfoResponse> findAllAdmins() {
        return userQueryRepository.findAllUserByRole(Role.ADMIN).stream()
                .map(UserInfoResponse::from)
                .toList();
    }
}

package com.runninghi.user.command.application.service;

import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminCommandService {
    private final UserCommandRepository userCommandRepository;

    // 전체 유저 정보 조회
    @Transactional(readOnly = true)
    public List<UserInfoResponse> findAllUsers() {
        return userCommandRepository.findAllByRole(Role.USER).stream()
                .map(UserInfoResponse::from)
                .toList();
    }

    // 전체 관리자 정보 조회
    @Transactional(readOnly = true)
    public List<UserInfoResponse> findAllAdmins() {
        return userCommandRepository.findAllByRole(Role.ADMIN).stream()
                .map(UserInfoResponse::from)
                .toList();
    }
}

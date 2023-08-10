package com.runninghi.user.command.application.service;

import com.runninghi.user.command.application.dto.response.UserInfoResponse;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserInfoResponse> getUsers() {
        return userRepository.findAllByType(Role.USER).stream()
                .map(UserInfoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserInfoResponse> getAdmins() {
        return userRepository.findAllByType(Role.ADMIN).stream()
                .map(UserInfoResponse::from)
                .toList();
    }
}

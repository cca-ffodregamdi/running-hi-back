package com.runninghi.user.command.application.service;

import com.runninghi.user.command.application.dto.request.UserUpdateRequest;
import com.runninghi.user.command.application.dto.response.UserDeleteResponse;
import com.runninghi.user.command.application.dto.response.UserInfoResponse;
import com.runninghi.user.command.application.dto.response.UserUpdateResponse;
import com.runninghi.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(UUID id) {
        return userRepository.findById(id)
                .map(UserInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    @Transactional
    public UserDeleteResponse deleteUser(UUID id) {
        if (!userRepository.existsById(id)) return new UserDeleteResponse(false);
        userRepository.deleteById(id);
        return new UserDeleteResponse(true);
    }

    @Transactional
    public UserUpdateResponse updateUser(UUID id, UserUpdateRequest request) {
        return userRepository.findById(id)
                .filter(user -> encoder.matches(request.password(), user.getPassword()))
                .map(user -> {
                    user.update(request, encoder);
                    return UserUpdateResponse.of(true, user);
                })
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
    }
}
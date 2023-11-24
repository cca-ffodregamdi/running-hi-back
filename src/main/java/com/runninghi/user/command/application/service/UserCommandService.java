package com.runninghi.user.command.application.service;

import com.runninghi.user.command.application.dto.user.request.UpdatePasswordRequest;
import com.runninghi.user.command.application.dto.user.request.UserUpdateRequest;
import com.runninghi.user.command.application.dto.user.response.UpdatePasswordResponse;
import com.runninghi.user.command.application.dto.user.response.UserDeleteResponse;
import com.runninghi.user.command.application.dto.user.response.UserUpdateResponse;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
import com.runninghi.user.query.infrastructure.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserCommandService {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordEncoder encoder;

    /* 비밀번호 변경 */
    @Transactional
    public UpdatePasswordResponse updatePassword(UpdatePasswordRequest request) {
        return userQueryRepository.findUserByAccount(request.account())
                .map(user -> {
                    System.out.println("user = " + user.getPassword());
                    user.updatePassword(request, encoder);
                    System.out.println("user.getPassword() = " + user.getPassword());
                    return UpdatePasswordResponse.from(true);
                })
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다."));
    }

    /* 회원 정보 수정 */
    @Transactional
    public UserUpdateResponse updateUser(UUID id, UserUpdateRequest request) {
        return userCommandRepository.findById(id)
                .filter(user -> encoder.matches(request.password(), user.getPassword()))
                .map(user -> {
                    user.update(request, encoder);
                    return UserUpdateResponse.of(true, user);
                })
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
    }

    /* 회원 탈퇴 */
    @Transactional
    public UserDeleteResponse deleteUser(UUID id) {
        if (!userCommandRepository.existsById(id)) return new UserDeleteResponse(false);
        userCommandRepository.deleteById(id);
        return new UserDeleteResponse(true);
    }

}

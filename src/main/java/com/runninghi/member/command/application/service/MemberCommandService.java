package com.runninghi.member.command.application.service;

import com.runninghi.member.command.application.dto.member.request.MemberUpdateRequest;
import com.runninghi.member.command.application.dto.member.request.UpdatePasswordRequest;
import com.runninghi.member.command.application.dto.member.response.MemberDeleteResponse;
import com.runninghi.member.command.application.dto.member.response.MemberUpdateResponse;
import com.runninghi.member.command.application.dto.member.response.UpdatePasswordResponse;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import com.runninghi.member.query.infrastructure.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberCommandService {
    private final MemberCommandRepository memberCommandRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final PasswordEncoder encoder;

    /* 비밀번호 변경 */
    @Transactional
    public UpdatePasswordResponse updatePassword(UpdatePasswordRequest request) {
        return memberQueryRepository.findUserByAccount(request.account())
                .map(member -> {
                    member.updatePassword(request, encoder);
                    return UpdatePasswordResponse.from(true);
                })
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다."));
    }

    /* 회원 정보 수정 */
    @Transactional
    public MemberUpdateResponse updateUser(UUID id, MemberUpdateRequest request) {
        return memberCommandRepository.findById(id)
                .filter(member -> encoder.matches(request.password(), member.getPassword()))
                .map(member -> {
                    member.update(request, encoder);
                    return MemberUpdateResponse.of(true, member);
                })
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
    }

    /* 회원 탈퇴 */
    @Transactional
    public MemberDeleteResponse deleteUser(UUID id) {
        if (!memberCommandRepository.existsById(id)) return new MemberDeleteResponse(false);
        memberCommandRepository.deleteById(id);
        return new MemberDeleteResponse(true);
    }

}

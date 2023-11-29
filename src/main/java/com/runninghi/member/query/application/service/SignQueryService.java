package com.runninghi.member.query.application.service;

import com.runninghi.member.command.application.dto.member.MemberInfoDTO;
import com.runninghi.member.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.member.query.infrastructure.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignQueryService {
    private final MemberQueryRepository userQueryRepository;

    // 아이디로 회원 정보 조회
    @Transactional(readOnly = true)
    public MemberInfoDTO findUserInfoByAccount(SignInRequest request) {
        return new MemberInfoDTO(userQueryRepository.findUserByAccount(request.account()));
    }
}

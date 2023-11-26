package com.runninghi.member.query.application.service;

import com.runninghi.member.command.application.dto.member.RefreshTokenDTO;
import com.runninghi.member.command.application.dto.member.request.FindAccountRequest;
import com.runninghi.member.command.application.dto.member.request.FindPasswordRequest;
import com.runninghi.member.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.member.command.domain.aggregate.Member;
import com.runninghi.member.query.application.dto.member.response.FindAccountResponse;
import com.runninghi.member.query.application.dto.member.response.FindPasswordResponse;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import com.runninghi.member.query.infrastructure.repository.MemberQueryRefreshTokenRepository;
import com.runninghi.member.query.infrastructure.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberQueryRepository userQueryRepository;
    private final MemberQueryRefreshTokenRepository userQueryRefreshTokenRepository;

    // 회원 정보 조회
    public MemberInfoResponse findMemberInfo(UUID id) {
        return userQueryRepository.findById(id)
                .map(MemberInfoResponse::from)
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
    public MemberInfoResponse findUserInfoByAccount(SignInRequest request) {
        return userQueryRepository.findUserByAccount(request.account())
                .map(MemberInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    // 리프레쉬 토큰 조회
    public RefreshTokenDTO findRefreshTokenById(Member member) {
        return new RefreshTokenDTO(userQueryRefreshTokenRepository.findById(member.getId()));
    }

}
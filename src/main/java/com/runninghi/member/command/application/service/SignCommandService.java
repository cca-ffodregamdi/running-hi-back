package com.runninghi.member.command.application.service;

import com.runninghi.configuration.jwt.TokenProvider;
import com.runninghi.member.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.member.command.application.dto.sign_in.response.SignInResponse;
import com.runninghi.member.command.application.dto.sign_up.request.SignUpRequest;
import com.runninghi.member.command.application.dto.sign_up.request.VerifyDuplicationIdRequest;
import com.runninghi.member.command.application.dto.sign_up.request.VerifyDuplicationNicknameRequest;
import com.runninghi.member.command.application.dto.sign_up.response.SignUpResponse;
import com.runninghi.member.command.application.dto.sign_up.response.VerifyDuplicationIdResponse;
import com.runninghi.member.command.application.dto.sign_up.response.VerifyDuplicationNicknameResponse;
import com.runninghi.member.command.domain.aggregate.entity.Member;
import com.runninghi.member.command.domain.aggregate.entity.MemberRefreshToken;
import com.runninghi.member.command.domain.repository.MemberCommandRefreshTokenRepository;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import com.runninghi.member.query.application.service.MemberQueryService;
import com.runninghi.member.query.application.service.SignQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional
public class SignCommandService {
    private final MemberCommandRepository memberCommandRepository;
    private final MemberCommandRefreshTokenRepository memberCommandRefreshTokenRepository;
    private final MemberQueryService memberQueryService;
    private final SignQueryService signQueryService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder;

    /* 아이디 중복 확인 */
    public VerifyDuplicationIdResponse verifyDuplicationId(VerifyDuplicationIdRequest request) {
        List<MemberInfoResponse> allMembers = memberQueryService.findAllMembers();
        if (request.account().startsWith("admin")) {
            return new VerifyDuplicationIdResponse(false);
        }
        for (MemberInfoResponse memberInfoResponse : allMembers) {
            log.info(memberInfoResponse);
            if (request.account().equals(memberInfoResponse.account())) {
                return new VerifyDuplicationIdResponse(false);
            }
        }
        return new VerifyDuplicationIdResponse(true);
    }

    /* 닉네임 중복 확인 */
    public VerifyDuplicationNicknameResponse verifyDuplicationNickname(VerifyDuplicationNicknameRequest request) {
        List<MemberInfoResponse> allMembers = memberQueryService.findAllMembers();
        for (MemberInfoResponse memberInfoResponse : allMembers) {
            log.info(memberInfoResponse);
            if (request.nickname().equals(memberInfoResponse.nickname())) {
                return new VerifyDuplicationNicknameResponse(false);
            }
        }
        return new VerifyDuplicationNicknameResponse(true);
    }

    /* 회원가입 */
    public SignUpResponse registUser(SignUpRequest request) {
        Member member = memberCommandRepository.save(Member.from(request, encoder));
        try {
            memberCommandRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }
        return SignUpResponse.from(member);
    }

    /* 로그인 */
    public SignInResponse signIn(SignInRequest request) {
        Member member = signQueryService.findUserInfoByAccount(request).getUserInfo()
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
        String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", member.getId(), member.getRole()));
        String refreshToken = tokenProvider.createRefreshToken();

        // 리프레쉬 토큰 있으면 갱신, 없으면 추가
        memberQueryService.findRefreshTokenById(member).getUserRefreshToken()
                .ifPresentOrElse(
                        it -> it.updateRefreshToken(refreshToken),
                        () -> memberCommandRefreshTokenRepository.save(new MemberRefreshToken(member, refreshToken))
                );
        return new SignInResponse(member.getName(), member.getNickname(), member.getEmail(), member.getRole(), accessToken, refreshToken);
    }
}

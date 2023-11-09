package com.runninghi.user.command.application.service;

import com.runninghi.configuration.jwt.TokenProvider;
import com.runninghi.user.command.application.dto.sign_in.request.SignInRequest;
import com.runninghi.user.command.application.dto.sign_in.response.SignInResponse;
import com.runninghi.user.command.application.dto.sign_up.request.SignUpRequest;
import com.runninghi.user.command.application.dto.sign_up.request.VerifyDuplicationIdRequest;
import com.runninghi.user.command.application.dto.sign_up.response.SignUpResponse;
import com.runninghi.user.command.application.dto.sign_up.response.VerifyDuplicationIdResponse;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.UserRefreshToken;
import com.runninghi.user.command.domain.repository.UserCommandRefreshTokenRepository;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
import com.runninghi.user.query.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.query.application.service.AdminQueryService;
import com.runninghi.user.query.application.service.SignQueryService;
import com.runninghi.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class SignCommandService {
    private final UserCommandRepository userCommandRepository;
    private final UserCommandRefreshTokenRepository userCommandRefreshTokenRepository;
    private final AdminQueryService adminQueryService;
    private final UserQueryService userQueryService;
    private final SignQueryService signQueryService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder;

    /* 아이디 중복 확인 */
    public VerifyDuplicationIdResponse verifyDuplicationId(VerifyDuplicationIdRequest request) {
        try {
            List<UserInfoResponse> allUsers = adminQueryService.findAllUsers();
            for (UserInfoResponse userInfoResponse : allUsers) {
                if (request.account().equals(userInfoResponse.account())) {
                    return new VerifyDuplicationIdResponse(false);
                }
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("으악");
        }
        return new VerifyDuplicationIdResponse(true);
    }

    /* 회원가입 */
    public SignUpResponse registUser(SignUpRequest request) {
        User user = userCommandRepository.save(User.from(request, encoder));
        try {
            userCommandRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }
        return SignUpResponse.from(user);
    }

    /* 로그인 */
    public SignInResponse signIn(SignInRequest request) {
        User user = signQueryService.findUserInfoByAccount(request).getUserInfo()
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
        String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", user.getId(), user.getRole()));
        String refreshToken = tokenProvider.createRefreshToken();

        // 리프레쉬 토큰 있으면 갱신, 없으면 추가
        userQueryService.findRefreshTokenById(user).getUserRefreshToken()
                .ifPresentOrElse(
                        it -> it.updateRefreshToken(refreshToken),
                        () -> userCommandRefreshTokenRepository.save(new UserRefreshToken(user, refreshToken))
                );
        return new SignInResponse(user.getName(), user.getNickname(), user.getEmail(), user.getRole(), accessToken, refreshToken);
    }
}

package com.runninghi.member.query.application.service;

import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import com.runninghi.member.query.infrastructure.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminQueryService {
    private final MemberQueryRepository memberQueryRepository;

    // 전체 유저 정보 조회
    public List<MemberInfoResponse> findAllMembers() {
        return memberQueryRepository.findAllMemberByRole(Role.USER).stream()
                .map(MemberInfoResponse::from)
                .toList();
    }

    // 전체 관리자 정보 조회
    public List<MemberInfoResponse> findAllAdmins() {
        return memberQueryRepository.findAllMemberByRole(Role.ADMIN).stream()
                .map(MemberInfoResponse::from)
                .toList();
    }
}

package com.runninghi.report.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.member.command.application.service.MemberCommandService;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import com.runninghi.member.query.application.service.MemberQueryService;
import com.runninghi.report.command.domain.service.ReportCommandDomainService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class ReportCommandInfraService implements ReportCommandDomainService {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

//    @Override
//    public ReportedUserResponse getReportedUserInfo(UUID reportedUserNo) {
//        UserInfoResponse userInfo = userQueryService.findUserInfo(reportedUserNo);
//
//        ReportedUserResponse reportedUserResponse = new ReportedUserResponse(userInfo.reportCount(), userInfo.blackListStatus());
//
//        return reportedUserResponse;
//    }

    @Override
    public void updateUserInfo(UUID reportedUserNo) {

        MemberInfoResponse memberInfo = memberQueryService.findMemberInfo(reportedUserNo);

        // 유저 업데이트 메소드 수정 후 작성
    }
}

package com.runninghi.postreport.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.member.query.application.dto.member.response.MemberInfoResponse;
import com.runninghi.member.query.application.service.MemberQueryService;
import com.runninghi.postreport.command.application.dto.response.ReportedUserResponse;
import com.runninghi.postreport.command.domain.service.PostReportCommandDomainService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class PostReportCommandInfraService implements PostReportCommandDomainService {

    private final MemberQueryService userQueryService;

    public ReportedUserResponse getReportedUserInfo(UUID reportedUserNo) {

        MemberInfoResponse userinfo = userQueryService.findMemberInfo(reportedUserNo);

        ReportedUserResponse reportedUserResponse = new ReportedUserResponse(userinfo.reportCount(), userinfo.blackListStatus());

        return reportedUserResponse;
    }
}

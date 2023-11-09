package com.runninghi.postreport.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.postreport.command.application.dto.response.ReportedUserResponse;
import com.runninghi.postreport.command.domain.service.PostReportCommandDomainService;
import com.runninghi.user.query.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class PostReportCommandInfraService implements PostReportCommandDomainService {

    private final UserQueryService userQueryService;

    public ReportedUserResponse getReportedUserInfo(UUID reportedUserNo) {

        UserInfoResponse userinfo = userQueryService.findUserInfo(reportedUserNo);

        ReportedUserResponse reportedUserResponse = new ReportedUserResponse(userinfo.reportCount(), userinfo.blackListStatus());

        return reportedUserResponse;
    }
}

package com.runninghi.postreport.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.postreport.command.application.dto.response.ReportedUserResponse;
import com.runninghi.postreport.command.domain.service.PostReportCommandDomainService;
import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.command.application.service.UserService;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class PostReportCommandInfraService implements PostReportCommandDomainService {

    private final UserService userService;

    public ReportedUserResponse getReportedUserInfo(UUID reportedUserNo) {

        UserInfoResponse userinfo = userService.findUserInfo(reportedUserNo);

        ReportedUserResponse reportedUserResponse = new ReportedUserResponse(userinfo.reportCount(), userinfo.blackListStatus());

        return reportedUserResponse;
    }
}

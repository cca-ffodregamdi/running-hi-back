package com.runninghi.postreport.command.infrastructure.service;

import com.runninghi.common.annotation.InfraService;
import com.runninghi.postreport.command.domain.service.PostReportCommandDomainService;
import com.runninghi.user.command.application.dto.user.response.UserInfoResponse;
import com.runninghi.user.command.application.service.UserCommandService;
import com.runninghi.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@InfraService
@RequiredArgsConstructor
public class PostReportCommandInfraService implements PostReportCommandDomainService {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

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

        UserInfoResponse userInfo = userQueryService.findUserInfo(reportedUserNo);

        // 유저 업데이트 메소드 수정 후 작성
    }
}

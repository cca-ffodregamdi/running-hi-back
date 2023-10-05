package com.runninghi.postreport.command.domain.service;

import com.runninghi.common.annotation.DomainService;

import java.util.UUID;

@DomainService
public interface PostReportCommandDomainService {
//    ReportedUserResponse getReportedUserInfo(UUID reportedUserNo);

    void updateUserInfo(UUID reportedUserNo);
}
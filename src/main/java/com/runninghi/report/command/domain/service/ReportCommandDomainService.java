package com.runninghi.report.command.domain.service;

import com.runninghi.common.annotation.DomainService;

import java.util.UUID;

@DomainService
public interface ReportCommandDomainService {
//    ReportedUserResponse getReportedUserInfo(UUID reportedUserNo);

    void updateUserInfo(UUID reportedUserNo);
}
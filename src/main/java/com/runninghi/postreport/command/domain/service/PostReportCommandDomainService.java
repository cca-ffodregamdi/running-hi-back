package com.runninghi.postreport.command.domain.service;

import com.runninghi.common.annotation.DomainService;
import com.runninghi.postreport.command.application.dto.response.ReportedUserResponse;

import java.util.UUID;

@DomainService
public interface PostReportCommandDomainService {

    ReportedUserResponse getReportedUserInfo(UUID reportedUserNo);
}
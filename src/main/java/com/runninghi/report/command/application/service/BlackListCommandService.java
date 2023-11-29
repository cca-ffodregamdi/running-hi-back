//package com.runninghi.report.command.application.service;
//
//import com.runninghi.common.handler.feedback.customException.NotFoundException;
//import com.runninghi.report.command.application.dto.request.ReportUpdateRequest;
//import com.runninghi.report.command.domain.aggregate.entity.Report;
//import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
//import com.runninghi.report.command.domain.repository.ReportCommandRepository;
//import com.runninghi.report.command.domain.service.ReportCommandDomainService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class BlackListCommandService {
//
//    private final ReportCommandRepository reportCommandRepository;
//    private final ReportCommandDomainService reportCommandDomainService;
//
//    // 설명. 신고 수락 시 피신고자 신고횟수 추가 기능
//    @Transactional
//    public int addReportCountToUser(ReportUpdateRequest request, Long reportNo) {
//
//        Report report = reportCommandRepository.findById(reportNo)
//                .orElseThrow(() -> new NotFoundException("해당하는 신고 내역이 없습니다."));
//
//        UUID reportedUserNo = report.getReportedUserVO().getReportedUserNo();   // 피신고자 번호
//
//        int originReportCount = reportCommandDomainService.getReportedUserInfo(reportedUserNo).reportCount();   // 기존 피신고 횟수
//
//        // 설명. 신고 수락 시 유저 신고횟수 +1
//        int newReportCount = request.processingStatus() == ProcessingStatus.ACCEPTED ? originReportCount + 1 : originReportCount;
//
//        return newReportCount;
//    }
//
//    // 설명. 피신고자 신고횟수 5회 이상 시 블랙리스트 변경
//    @Transactional
//    public boolean updateBlackListStatus(UUID userNo) {
//
//        int reportCount = reportCommandDomainService.getReportedUserInfo(userNo).reportCount();
//
//        boolean blackListStatus = reportCommandDomainService.getReportedUserInfo(userNo).blacklistStatus();
//
//        if (reportCount >= 5) {
//            blackListStatus = true;
//        }
//
//        return blackListStatus;
//    }
//}

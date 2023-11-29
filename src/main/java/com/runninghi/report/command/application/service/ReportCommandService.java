package com.runninghi.report.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.report.command.application.dto.request.ReportSaveRequest;
import com.runninghi.report.command.application.dto.response.ReportDeleteResponse;
import com.runninghi.report.command.application.dto.response.ReportResponse;
import com.runninghi.report.command.domain.aggregate.entity.Report;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ReportType;
import com.runninghi.report.command.domain.aggregate.vo.ReportedContentVO;
import com.runninghi.report.command.domain.repository.ReportCommandRepository;
import com.runninghi.report.command.domain.service.ReportCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReportCommandService {

    private final ReportCommandRepository reportCommandRepository;
    private final ReportCommandDomainService reportCommandDomainService;

    @Transactional
    public ReportResponse saveReport(ReportSaveRequest reportSaveRequest) {

        if (reportSaveRequest.reportCategoryCode() == 0) {
            throw new IllegalArgumentException("신고 카테고리를 선택해주세요");
        }

        if (reportSaveRequest.reportContent().length() > 100) {
            throw new IllegalArgumentException("신고 내용은 100자를 넘을 수 없습니다.");
        }
        ReportType reportType = reportSaveRequest.reportType().equals("post") ? ReportType.POST : ReportType.COMMENT;

        Report report = Report.builder()
                .reportType(reportType)
                .reportCategoryCode(reportSaveRequest.reportCategoryCode())
                .reportContent(reportSaveRequest.reportContent())
                .reportedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .processingStatus(ProcessingStatus.INPROGRESS)
//                .reportUserVO(new ReportUserVO(reportSaveRequest.reportUserNo()))
//                .reportedUserVO(new ReportedUserVO(reportSaveRequest.reportedUserNo()))
//                .reportedContentVO(new ReportedContentVO(reportSaveRequest.reportedPostNo()))
                .build();

        reportCommandRepository.save(report);

        return ReportResponse.from(report);
    }

    // 설명. 신고 수락 여부에 따른 신고처리상태 및 유저 신고횟수, (게시글 신고횟수) 변경
//    @Transactional
//    public ReportResponse updateReport(ReportUpdateRequest request, Long reportNo) {
//
//        Report report = reportCommandRepository.findById(reportNo)
//                .orElseThrow(() -> new NotFoundException("해당하는 신고 내역이 없습니다."));
//
//        report.update(request);
//        reportCommandRepository.save(report);
//
//        UUID reportedUserNo = report.getReportedUserVO().getReportedUserNo();   // 피신고자 번호
//
//        // 설명. 신고 수락 시 유저 신고횟수 +1
//        if (request.processingStatus() == ProcessingStatus.ACCEPTED) {  //필기. request 넘어오는값 어떻게 할건지?
//            reportCommandDomainService.updateUserInfo(reportedUserNo);
//        }
//
//        return ReportResponse.from(report);     // 나중에 업데이트된 내용 새로운 dto에 담아서 리턴하도록 수정
//    }

    @Transactional
    public ReportDeleteResponse deleteReport(Long reportNo) {

        Report report = reportCommandRepository.findById(reportNo)
                .orElseThrow(() -> new NotFoundException("해당하는 신고 내역이 없습니다."));

        reportCommandRepository.delete(report);

        return new ReportDeleteResponse(true);
    }
}

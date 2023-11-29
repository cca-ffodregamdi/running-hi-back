package com.runninghi.report.query.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.report.command.application.dto.response.ReportResponse;
import com.runninghi.report.command.domain.aggregate.entity.Report;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.report.query.infrastructure.repository.ReportQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportQueryService {

    private final ReportQueryRepository reportQueryRepository;

    // 설명. 모든 게시글 신고 조회
    @Transactional
    public List<ReportResponse> getAllReports() {

        List<ReportResponse> reportList = reportQueryRepository.findAll().stream()
                .map(ReportResponse::from)
                .toList();

//        if (reportList.size() == 0) {
//            throw new NotFoundException("신고 내역이 없습니다.");
//        }

        return reportList;
    }

    // 설명. 게시글 신고 상세 조회
    @Transactional
    public ReportResponse getReportByReportNo(Long reportNo) {

        // 필기. findById는 Optional<Report> 형태로 객체 반환.
        Report report = reportQueryRepository.findById(reportNo)
                .orElseThrow(() -> new NotFoundException("해당하는 신고 내역이 없습니다."));

        // 설명. Entity -> DTO 변환
        ReportResponse reportResponse = ReportResponse.from(report);

        return reportResponse;
    }

    // 설명. 신고처리 상태로 게시글신고 조회
    @Transactional
    public List<ReportResponse> getReportByProcessingStatus(ProcessingStatus processingStatus) {

        List<ReportResponse> reportList = reportQueryRepository.findByProcessingStatus(processingStatus).stream()
                .map(ReportResponse::from)
                .toList();

        return reportList;
    }
}

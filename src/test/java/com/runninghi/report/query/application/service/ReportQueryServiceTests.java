package com.runninghi.report.query.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.report.command.application.dto.request.ReportSaveRequest;
import com.runninghi.report.command.application.dto.request.ReportUpdateRequest;
import com.runninghi.report.command.application.dto.response.ReportResponse;
import com.runninghi.report.command.application.service.ReportCommandService;
import com.runninghi.report.command.domain.aggregate.entity.Report;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.report.query.infrastructure.repository.ReportQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class ReportQueryServiceTests {

    @Autowired
    private ReportQueryRepository reportQueryRepository;

    @Autowired
    private ReportCommandService reportCommandService;

    @Autowired
    private ReportQueryService reportQueryService;

    @BeforeEach
    void clear() {
        reportQueryRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("게시글 신고 조회 테스트: 상세조회 성공")
    void findReportTest() {

        //given
        ReportSaveRequest reportSaveRequest = new ReportSaveRequest("post",2, "홍보 게시물",
                UUID.randomUUID(), UUID.randomUUID(), 1L);

        //when
        ReportResponse savedReport = reportCommandService.saveReport(reportSaveRequest);
        Report findedReport = reportQueryRepository.findById(savedReport.reportNo()).get();

        //then
        assertThat(findedReport.getReportContent().equals("홍보 게시물"));
        assertThat(findedReport.getReportCategoryCode()).isEqualTo(2);
    }

//    @Test
//    @DisplayName("게시글 신고 조회 테스트: status INPROGRESS만 조회 성공")
//    void findInProgressReportTest() {
//
//        //given
//        reportCommandService.saveReport(new ReportSaveRequest(1, "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));
//        reportCommandService.saveReport(new ReportSaveRequest(2, "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));
//        reportCommandService.saveReport(new ReportSaveRequest(3, "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));
//        ReportResponse reportResponse = reportCommandService.saveReport(new ReportSaveRequest(4, "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));
//        System.out.println(reportResponse.reportNo());
//
//        reportCommandService.updateReport(new ReportUpdateRequest(ProcessingStatus.REJECTED), reportResponse.reportNo());
//
//        //when
//        List<Report> inProgressReportList = reportQueryRepository.findByProcessingStatus(ProcessingStatus.INPROGRESS);
//
//        //then
//        Assertions.assertEquals(3, inProgressReportList.size());
//    }

    @Test
    @DisplayName("게시글 신고 조회 테스트: 전체조회 성공")
    void findReportListTest() {

        //given
        reportCommandService.saveReport(new ReportSaveRequest("comment", 1, "욕설", UUID.randomUUID(), UUID.randomUUID(), 1L));
        reportCommandService.saveReport(new ReportSaveRequest("comment", 1, "욕설", UUID.randomUUID(), UUID.randomUUID(), 1L));
        reportCommandService.saveReport(new ReportSaveRequest("comment", 1, "욕설", UUID.randomUUID(), UUID.randomUUID(), 1L));

        //when
        List<Report> reportList = reportQueryRepository.findAll();

        //then
        Assertions.assertEquals(3, reportList.size());
    }

    @Test
    @DisplayName("게시글 신고 조회 테스트: 상세 조회할 게시글 신고 번호 없을 시 예외처리 확인")
    void doesNotExistReportTest() {

        //given
        reportCommandService.saveReport(new ReportSaveRequest("comment", 1, "욕설", UUID.randomUUID(), UUID.randomUUID(), 1L));

        //when
        //then
        assertThatThrownBy(() -> reportQueryService.getReportByReportNo(10L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당하는 신고 내역이 없습니다.");
    }

//    @Test
//    @DisplayName("게시글 신고 조회 테스트: 전체 조회할 게시글 신고 목록 없을 시 예외처리 확인")
//    void doesNotExistReportsTest() {
//
//        //given
//        //when
//        //then
//        assertThatThrownBy(() -> reportQueryService.findAllReports())
//                .isInstanceOf(NotFoundException.class)
//                .hasMessage("신고 내역이 없습니다.");
//    }
}

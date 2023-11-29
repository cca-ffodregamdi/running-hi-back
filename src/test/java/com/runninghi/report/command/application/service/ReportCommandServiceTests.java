package com.runninghi.report.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import com.runninghi.report.command.application.dto.request.ReportSaveRequest;
import com.runninghi.report.command.application.dto.request.ReportUpdateRequest;
import com.runninghi.report.command.application.dto.response.ReportResponse;
import com.runninghi.report.command.domain.aggregate.entity.Report;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.report.command.domain.repository.ReportCommandRepository;
import com.runninghi.report.query.application.service.ReportQueryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class ReportCommandServiceTests {

    @Autowired
    private ReportCommandRepository reportCommandRepository;

    @Autowired
    private ReportCommandService reportCommandService;

    @Autowired
    private ReportQueryService reportQueryService;

    @Autowired
    private MemberCommandRepository memberCommandRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void clear() {
        reportCommandRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("게시글 신고 저장 테스트: DTO 엔티티 변환 후 저장 확인")
    void saveReportTest() {

        //given
        Long before = reportCommandRepository.count();

        //when
        ReportSaveRequest reportSaveRequest = new ReportSaveRequest("post",1, "욕설",
                UUID.randomUUID(), UUID.randomUUID(), 1L);

        Report report = Report.builder()
                .reportCategoryCode(reportSaveRequest.reportCategoryCode())
                .reportContent(reportSaveRequest.reportContent())
                .reportedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .processingStatus(ProcessingStatus.INPROGRESS)
//                .reportUserVO(new reportUserVO(reportSaveRequest.reportUserNo()))
//                .reportedUserVO(new reportedUserVO(reportSaveRequest.reportedUserNo()))
//                .reportedContentVO(new ReportedContentVO(reportSaveRequest.reportedPostNo()))
                .build();

        reportCommandRepository.save(report);
        Long after = reportCommandRepository.count();

        //then
        Assertions.assertEquals(1, after - before);
    }

    @Test
    @DisplayName("게시글 신고 저장 테스트: 카테고리 미선택 시 저장 안하는지 확인")
    void checkReportCategoryCodeTest() {

        //given
        Long before = reportCommandRepository.count();

        ReportSaveRequest reportSaveRequest = new ReportSaveRequest("post",0, "욕설",
                UUID.randomUUID(), UUID.randomUUID(), 1L);

        //when
        // 필기. 예외의 유형, () -> 테스트할 코드
        Assertions.assertThrows(IllegalArgumentException.class, () -> reportCommandService.saveReport(reportSaveRequest));
        Long after = reportCommandRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }

    @Test
    @DisplayName("게시글 신고 저장 테스트: 신고 내용 100자 초과일 시 저장 안하는지 확인")
    void checkReportContentLengthTest() {

        //given
        Long before = reportCommandRepository.count();

        String str = "a".repeat(101);

        ReportSaveRequest reportSaveRequest = new ReportSaveRequest("post",1, str,
                UUID.randomUUID(), UUID.randomUUID(), 1L);

        //when
        assertThatThrownBy(() -> reportCommandService.saveReport(reportSaveRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("신고 내용은 100자를 넘을 수 없습니다.");

        Long after = reportCommandRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }

    @Test
    @DisplayName("게시글 신고 수정 테스트: 수정 성공 확인")
    void updateReportTest() {

        ReportResponse reportResponse = reportCommandService.saveReport(new ReportSaveRequest("post", 1,
                "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));

        Report report = reportCommandRepository.findById(reportResponse.reportNo()).get();

        report.update(new ReportUpdateRequest(ProcessingStatus.ACCEPTED));

        Assertions.assertEquals(ProcessingStatus.ACCEPTED, report.getProcessingStatus());
    }

//    @Test
//    @DisplayName("게시글 신고 수정 테스트: 관리자가 신고 수락한 경우 신고 status 'ACCEPTED'로 변경 확인")
//    void checkAcceptedStatus() {
//
//        // given
//        Member reportedUser = memberCommandRepository.save(Member.builder()
//                .account("qwerty1234")
//                .password(encoder.encode("1234"))
//                .name("김철수")
//                .nickname("qwe")
//                .email("qwe@qwe.qw")
//                .role(Role.USER)
//                .status(true)
//                .reportCount(1)
//                .build());
//
//        ReportResponse reportResponse = reportCommandService.saveReport(new ReportSaveRequest("post", 1,
//                "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));
//
//        Report report = reportCommandRepository.findById(reportResponse.reportNo()).get();
//
//        ReportUpdateRequest request = new ReportUpdateRequest(ProcessingStatus.ACCEPTED);
//
//        // when
//        reportCommandService.updateReport(request, report.getReportNo());
//
//        // then
//        Assertions.assertEquals(ProcessingStatus.ACCEPTED, report.getProcessingStatus());
//    }

    //    @Test
//    @DisplayName("게시글 신고 수락 테스트: 관리자가 신고 수락 시 게시글 상태값 true(조회 안됨) 변경")          // userPost merge 후 작성
//    void updatePostStatusTrue() {
//    }

//    @Test
//    @DisplayName("게시글 신고 거절 테스트: 관리자가 신고 거절한 경우 신고 status 'REJECTED'로 변경 확인")
//    void checkRejectedStatus() {
//
//        // given
//        ReportResponse reportResponse = reportCommandService.saveReport(new ReportSaveRequest("post", 1,
//                "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));
//
//        Report report = reportCommandRepository.findById(reportResponse.reportNo()).get();
//
//        ReportUpdateRequest request = new ReportUpdateRequest(ProcessingStatus.REJECTED);
//
//        // when
//        reportCommandService.updateReport(request, report.getReportNo());
//
//        // then
//        Assertions.assertEquals(ProcessingStatus.REJECTED, report.getProcessingStatus());
//    }

    @Test
    @DisplayName("게시글 신고 삭제 테스트: 삭제 성공 확인")
    void deleteReportTest() {

        //given
        ReportSaveRequest reportSaveRequest = new ReportSaveRequest("post",2, "홍보 게시물",
                UUID.randomUUID(), UUID.randomUUID(), 1L);

        ReportResponse savedReport = reportCommandService.saveReport(reportSaveRequest);

        //when
        reportCommandService.deleteReport(savedReport.reportNo());

        //then
        assertThatThrownBy(() -> reportQueryService.getReportByReportNo(savedReport.reportNo()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당하는 신고 내역이 없습니다.");
    }

    @Test
    @DisplayName("게시글 신고 삭제 테스트: 상세 조회 시 해당하는 신고 내역 없을 시 예외처리 확인")
    void doesNotExistReportTest() {

        //given
        ReportSaveRequest reportSaveRequest = new ReportSaveRequest("post",2, "홍보 게시물",
                UUID.randomUUID(), UUID.randomUUID(), 1L);

        reportCommandService.saveReport(reportSaveRequest);

        Long before = reportCommandRepository.count();

        //when
        assertThatThrownBy(() -> reportCommandService.deleteReport(10L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당하는 신고 내역이 없습니다.");

        Long after = reportCommandRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }

}

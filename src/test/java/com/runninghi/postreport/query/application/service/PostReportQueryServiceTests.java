package com.runninghi.postreport.query.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.postreport.command.application.dto.request.PostReportSaveRequest;
import com.runninghi.postreport.command.application.dto.request.PostReportUpdateRequest;
import com.runninghi.postreport.command.application.service.PostReportCommandService;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.postreport.query.infrastructure.repository.PostReportQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class PostReportQueryServiceTests {

    @Autowired
    private PostReportQueryRepository postReportQueryRepository;

    @Autowired
    private PostReportCommandService postReportCommandService;

    @Autowired
    private PostReportQueryService postReportQueryService;

    @Test
    @DisplayName("게시글 신고 조회 테스트: 상세조회 성공")
    void findPostReportTest() {

        //given
        PostReportSaveRequest postReportSaveRequest = new PostReportSaveRequest(2, "홍보 게시물",
                UUID.randomUUID(), UUID.randomUUID(), 1L);

        //when
        PostReport savedPostReport = postReportCommandService.savePostReport(postReportSaveRequest);
        PostReport findedPostReport = postReportQueryRepository.findById(savedPostReport.getPostReportNo()).get();

        //then
        assertThat(findedPostReport.getPostReportContent().equals("홍보 게시물"));
        assertThat(findedPostReport.getPostReportCategoryCode()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 신고 조회 테스트: status INPROGRESS만 조회 성공")
    void findInProgressPostReportTest() {

        //given
        postReportCommandService.savePostReport(new PostReportSaveRequest(1, "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));
        postReportCommandService.savePostReport(new PostReportSaveRequest(1, "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));
        postReportCommandService.savePostReport(new PostReportSaveRequest(1, "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));
        PostReport postReport = postReportCommandService.savePostReport(new PostReportSaveRequest(1, "홍보", UUID.randomUUID(), UUID.randomUUID(), 1L));

        postReport.update(new PostReportUpdateRequest(ProcessingStatus.ACCEPTED));

        //when
        List<PostReport> inProgressPostReportList = postReportQueryRepository.findByProcessingStatus(ProcessingStatus.INPROGRESS);

        //then
        Assertions.assertEquals(3, inProgressPostReportList.size());
    }

    @Test
    @DisplayName("게시글 신고 조회 테스트: 전체조회 성공")
    void findPostReportListTest() {

        //given
        postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설", UUID.randomUUID(), UUID.randomUUID(), 1L));
        postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설", UUID.randomUUID(), UUID.randomUUID(), 1L));
        postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설", UUID.randomUUID(), UUID.randomUUID(), 1L));

        //when
        List<PostReport> postReportList = postReportQueryRepository.findAll();

        //then
        Assertions.assertEquals(3, postReportList.size());
    }

    @Test
    @DisplayName("게시글 신고 조회 테스트: 상세 조회할 게시글 신고 번호 없을 시 예외처리 확인")
    void doesNotExistPostReportTest() {

        //given
        postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설", UUID.randomUUID(), UUID.randomUUID(), 1L));

        //when
        //then
        assertThatThrownBy(() -> postReportQueryService.findPostReport(10L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당하는 신고 내역이 없습니다.");
    }

    @Test
    @DisplayName("게시글 신고 조회 테스트: 전체 조회할 게시글 신고 목록 없을 시 예외처리 확인")
    void doesNotExistPostReportsTest() {

        //given
        //when
        //then
        assertThatThrownBy(() -> postReportQueryService.findAllPostReports())
                .isInstanceOf(NotFoundException.class)
                .hasMessage("신고 내역이 없습니다.");
    }
}

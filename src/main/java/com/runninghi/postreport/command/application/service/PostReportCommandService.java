package com.runninghi.postreport.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.postreport.command.application.dto.request.PostReportSaveRequest;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import com.runninghi.postreport.command.domain.repository.PostReportCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostReportCommandService {

    private final PostReportCommandRepository postReportCommandRepository;

    @Transactional
    public PostReport savePostReport(PostReportSaveRequest postReportSaveRequest) {

        if (postReportSaveRequest.postReportCategoryCode() == 0) {
            throw new IllegalArgumentException("신고 카테고리를 선택해주세요");
        }

        if (postReportSaveRequest.postReportContent().isEmpty()) {
            throw new IllegalArgumentException("신고 내용을 입력해주세요");
        }

        if (postReportSaveRequest.postReportContent().length() > 100) {
            throw new IllegalArgumentException("신고 내용은 100자를 넘을 수 없습니다.");
        }

        PostReport postReport = PostReport.builder()
                .postReportCategoryCode(postReportSaveRequest.postReportCategoryCode())
                .postReportContent(postReportSaveRequest.postReportContent())
                .postReportedDate(LocalDateTime.now())
                .processingStatus(ProcessingStatus.INPROGRESS)
                .postReportUserVO(new PostReportUserVO(postReportSaveRequest.reportUserNo()))
                .postReportedUserVO(new PostReportedUserVO(postReportSaveRequest.reportedUserNo()))
                .reportedPostVO(new ReportedPostVO(postReportSaveRequest.reportedPostNo()))
                .build();

        postReportCommandRepository.save(postReport);

        return postReport;
    }

    @Transactional
    public void deletePostReport(Long postReportNo) {

        PostReport postReport = postReportCommandRepository.findById(postReportNo)
                .orElseThrow(() -> new NotFoundException("해당하는 신고 내역이 없습니다."));

        postReportCommandRepository.delete(postReport);

    }
}

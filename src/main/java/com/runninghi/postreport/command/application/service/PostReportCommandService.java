package com.runninghi.postreport.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.postreport.command.application.dto.request.PostReportRequest;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import com.runninghi.postreport.command.domain.repository.PostReportCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostReportCommandService {

    private final PostReportCommandRepository postReportCommandRepository;

    @Transactional
    public PostReport savePostReport(PostReportRequest postReportRequest, UUID reportUserNo,
                                     UUID reportedUserNo, Long reportedPostNo) {

        if (postReportRequest.postReportCategoryCode() == 0) {
            throw new IllegalArgumentException("신고 카테고리를 선택해주세요");
        }

        if (postReportRequest.postReportContent().isEmpty()) {
            throw new IllegalArgumentException("신고 내용을 입력해주세요");
        }

        if (postReportRequest.postReportContent().length() > 100) {
            throw new IllegalArgumentException("신고 내용은 100자를 넘을 수 없습니다.");
        }

        PostReport postReport = PostReport.builder()
                .postReportCategoryCode(postReportRequest.postReportCategoryCode())
                .postReportContent(postReportRequest.postReportContent())
                .postReportedDate(LocalDateTime.now())
                .postReportUserVO(new PostReportUserVO(reportUserNo))
                .postReportedUserVO(new PostReportedUserVO(reportedUserNo))
                .reportedPostVO(new ReportedPostVO(reportedPostNo))
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

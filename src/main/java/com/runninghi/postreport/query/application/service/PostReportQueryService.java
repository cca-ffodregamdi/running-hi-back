package com.runninghi.postreport.query.application.service;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.postreport.command.application.dto.response.PostReportResponse;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.postreport.query.infrastructure.repository.PostReportQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReportQueryService {

    private final PostReportQueryRepository postReportQueryRepository;

    // 설명. 게시글 신고 상세 조회
    @Transactional
    public PostReportResponse findPostReport(Long postReportNo) {

        // 필기. findById는 Optional<PostReport> 형태로 객체 반환.
        PostReport postReport = postReportQueryRepository.findById(postReportNo)
                .orElseThrow(() -> new NotFoundException("해당하는 신고 내역이 없습니다."));

        // 설명. Entity -> DTO 변환
        PostReportResponse postReportResponse = PostReportResponse.from(postReport);

        return postReportResponse;
    }

    // 설명. 신고처리 상태로 게시글신고 조회
    @Transactional
    public List<PostReport> findPostReportByProcessingStatus(ProcessingStatus processingStatus) {
        List<PostReport> postReportList = postReportQueryRepository.findByProcessingStatus(processingStatus);

        return postReportList;
    }

    // 설명. 모든 게시글 신고 조회
    @Transactional
    public List<PostReportResponse> findAllPostReports() {

        List<PostReportResponse> postReportList = postReportQueryRepository.findAll().stream()
                .map(PostReportResponse::from)
                .toList();

        if(postReportList.size() == 0) {
            throw new NotFoundException("신고 내역이 없습니다.");
        }

        return postReportList;
    }
}

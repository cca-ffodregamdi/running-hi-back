package com.runninghi.postreport.query.application.service;

import com.runninghi.postreport.command.application.dto.response.PostReportResponse;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindPostReportService {

    private PostReportRepository postReportRepository;

    // 설명. 게시글 신고 상세 조회
    public PostReportResponse findPostReport(Long postReportNo) {

        // 설명. findById는 Optional<PostReport> 형태로 객체 반환. get()을 통해 Optional 안의 객체를 꺼냄.
        PostReport postReport = postReportRepository.findById(postReportNo).get();

        if(postReport == null) {
            throw new IllegalArgumentException("해당하는 신고 내역이 없습니다.");
        }

        // 설명. Entity -> DTO 변환
        PostReportResponse postReportResponse = PostReportResponse.from(postReport);

        return postReportResponse;
    }

    // 설명. 모든 게시글 신고 조회
    public List<PostReportResponse> findAllPostReports() {

        List<PostReportResponse> postReportList = postReportRepository.findAll().stream()
                .map(PostReportResponse::from)
                .toList();

        return postReportList;
    }
}

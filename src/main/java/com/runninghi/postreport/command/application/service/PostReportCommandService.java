package com.runninghi.postreport.command.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.postreport.command.application.dto.request.PostReportSaveRequest;
import com.runninghi.postreport.command.application.dto.request.PostReportUpdateRequest;
import com.runninghi.postreport.command.application.dto.response.PostReportDeleteResponse;
import com.runninghi.postreport.command.application.dto.response.PostReportResponse;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import com.runninghi.postreport.command.domain.repository.PostReportCommandRepository;
import com.runninghi.postreport.command.domain.service.PostReportCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostReportCommandService {

    private final PostReportCommandRepository postReportCommandRepository;
    private final PostReportCommandDomainService postReportCommandDomainService;

    @Transactional
    public PostReportResponse savePostReport(PostReportSaveRequest postReportSaveRequest) {

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

        return PostReportResponse.from(postReport);
    }

    // 설명. 신고 수락 여부에 따른 신고처리상태 및 유저 신고횟수 변경
    @Transactional
    public PostReportResponse updatePostReport(PostReportUpdateRequest request, Long postReportNo) {

        PostReport postReport = postReportCommandRepository.findById(postReportNo)
                .orElseThrow(() -> new NotFoundException("해당하는 신고 내역이 없습니다."));

        postReport.update(request);

        UUID reportedUserNo = postReport.getPostReportedUserVO().getPostReportedUserNo();   // 피신고자 번호

        // 설명. 신고 수락 시 유저 신고횟수 +1
        if (request.processingStatus() == ProcessingStatus.ACCEPTED) {
            postReportCommandDomainService.updateUserInfo(reportedUserNo);
        }

        return PostReportResponse.from(postReport);     // 나중에 업데이트된 내용 새로운 dto에 담아서 리턴하도록 수정
    }


    @Transactional
    public PostReportDeleteResponse deletePostReport(Long postReportNo) {

        PostReport postReport = postReportCommandRepository.findById(postReportNo)
                .orElseThrow(() -> new NotFoundException("해당하는 신고 내역이 없습니다."));

        postReportCommandRepository.delete(postReport);

        return new PostReportDeleteResponse(true);
    }
}

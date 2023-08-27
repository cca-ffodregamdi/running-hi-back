package com.runninghi.postreport.command.application.service;

import com.runninghi.postreport.command.application.dto.request.PostReportProcessingRequest;
import com.runninghi.postreport.command.application.dto.request.PostReportUpdateRequest;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.postreport.command.domain.repository.PostReportCommandRepository;
import com.runninghi.postreport.command.domain.service.PostReportCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlackListCommandService {

    private final PostReportCommandRepository postReportCommandRepository;
    private final PostReportCommandDomainService postReportCommandDomainService;

    // 설명. 신고 수락 여부에 따라 신고 처리상태 변경 기능
    @Transactional
    public void updatePostReportStatus(PostReportProcessingRequest postReportProcessingDTO) {

        PostReport postReport = postReportCommandRepository.findById(postReportProcessingDTO.postReportNo()).get();

            ProcessingStatus processingStatus = postReportProcessingDTO.isAccepted() ?
                    ProcessingStatus.ACCEPTED : ProcessingStatus.REJECTED;
            postReport.update(new PostReportUpdateRequest(processingStatus));
    }

    // 설명. 신고 수락 시 피신고자 신고횟수 추가 기능
    @Transactional
    public int addReportCountToUser(PostReportProcessingRequest postReportProcessingDTO) {

        PostReport postReport = postReportCommandRepository.findById(postReportProcessingDTO.postReportNo()).get();

        UUID reportedUserNo = postReport.getPostReportedUserVO().getPostReportedUserNo();   // 피신고자 번호

        int originReportCount = postReportCommandDomainService.getReportedUserInfo(reportedUserNo).reportCount();   // 기존 피신고 횟수

        // 설명. 신고 수락 시 유저 신고횟수 +1
        int newReportCount = postReportProcessingDTO.isAccepted() ? originReportCount +1 : originReportCount;

        return newReportCount;
    }

    // 설명. 피신고자 신고횟수 5회 이상 시 블랙리스트 변경
    @Transactional
    public boolean updateBlackListStatus(UUID userNo) {

        int reportCount = postReportCommandDomainService.getReportedUserInfo(userNo).reportCount();

        boolean blackListStatus = postReportCommandDomainService.getReportedUserInfo(userNo).blacklistStatus();

        if(reportCount >= 5) {
            blackListStatus = true;
        }

        return blackListStatus;
    }
}

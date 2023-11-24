package com.runninghi.postreport.command.application.service;

import com.runninghi.member.command.domain.aggregate.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import com.runninghi.postreport.command.application.dto.request.PostReportProcessingRequest;
import com.runninghi.postreport.command.application.dto.request.PostReportSaveRequest;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.postreport.command.domain.repository.PostReportCommandRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class BlackListCommandServiceTests {

    @Autowired
    private PostReportCommandRepository postReportCommandRepository;

    @Autowired
    private MemberCommandRepository userCommandRepository;

    @Autowired
    private BlackListCommandService blackListCommandService;

    @Autowired
    private PostReportCommandService postReportCommandService;

    @Autowired
    private PasswordEncoder encoder;

    @AfterEach
    void clear() {
        postReportCommandRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("게시글 신고 수락 테스트: 관리자가 신고 수락한 경우 신고 status 'ACCEPTED'로 변경 확인")
    void checkAcceptedStatus() {

        // given
        PostReport postReport = postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설",
                UUID.randomUUID(), UUID.randomUUID(), 11L));

        PostReportProcessingRequest postReportProcessingDTO = new PostReportProcessingRequest(true, postReport.getPostReportNo());

        blackListCommandService.updatePostReportStatus(postReportProcessingDTO);

        // when
        PostReport updatedPostReport = postReportCommandRepository.findById(postReport.getPostReportNo()).get();

        // then
        Assertions.assertEquals(ProcessingStatus.ACCEPTED, updatedPostReport.getProcessingStatus());
    }

    //    @Test
//    @DisplayName("게시글 신고 수락 테스트: 관리자가 신고 수락 시 게시글 상태값 true(조회 안됨) 변경")          // userPost merge 후 작성
//    void updatePostStatusTrue() {
//    }
    @Test
    @DisplayName("게시글 신고 거절 테스트: 관리자가 신고 거절한 경우 신고 status 'REJECTED'로 변경 확인")
    void checkRejectedStatus() {

        // given
        PostReport postReport = postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설",
                UUID.randomUUID(), UUID.randomUUID(), 11L));

        PostReportProcessingRequest postReportProcessingDTO = new PostReportProcessingRequest(false, postReport.getPostReportNo());

        blackListCommandService.updatePostReportStatus(postReportProcessingDTO);

        // when
        PostReport updatedPostReport = postReportCommandRepository.findById(postReport.getPostReportNo()).get();

        // then
        Assertions.assertEquals(ProcessingStatus.REJECTED, updatedPostReport.getProcessingStatus());
    }

    @Test
    @DisplayName("게시글 신고 수락 테스트: 관리자가 게시글 신고 수락한 경우 유저 신고횟수 +1")
    void checkUserReportCount() {

        // given
        Member savedMember = userCommandRepository.save(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .reportCount(1)
                .build());

        PostReport postReport = postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설",
                UUID.randomUUID(), savedMember.getId(), 11L));

        PostReportProcessingRequest postReportProcessingDTO = new PostReportProcessingRequest(true, postReport.getPostReportNo());

        // when
        int newReportCount = blackListCommandService.addReportCountToUser(postReportProcessingDTO);

        // then
        Assertions.assertEquals(2, newReportCount);
    }


//    @Test
//    @DisplayName("게시글 신고 거절 테스트: 관리자가 신고 거절 시 게시글 상태값 false(조회 됨) 유지")    // userPost merge 후 작성
//    void checkPostStatusFalse() {
//    }

    @Test
    @DisplayName("게시글 신고 거절 테스트: 관리자가 게시글 신고 거절한 경우 유저 신고횟수 동일 확인")
    void checkSameUserReportCount() {

        // given
        Member savedMember = userCommandRepository.save(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .reportCount(1)
                .build());

        PostReport postReport = postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설",
                UUID.randomUUID(), savedMember.getId(), 11L));

        PostReportProcessingRequest postReportProcessingDTO = new PostReportProcessingRequest(false, postReport.getPostReportNo());

        // when
        int newReportCount = blackListCommandService.addReportCountToUser(postReportProcessingDTO);

        // then
        Assertions.assertEquals(1, newReportCount);
    }

    @Test
    @DisplayName("블랙리스트 변경 테스트: 유저 신고횟수 5회 이상인 경우 블랙리스트 상태값 true 지정")
    void updateBlackListStatus() {

        // given
        Member savedMember = userCommandRepository.save(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .reportCount(5)
                .build());

        // when
        boolean blackListStatus = blackListCommandService.updateBlackListStatus(savedMember.getId());

        // then
        Assertions.assertTrue(blackListStatus);
    }
}

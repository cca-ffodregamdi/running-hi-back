package com.runninghi.postreport.command.application.service;

import com.runninghi.postreport.command.application.dto.request.PostReportProcessingRequest;
import com.runninghi.postreport.command.application.dto.request.PostReportSaveRequest;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.postreport.command.domain.repository.PostReportCommandRepository;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class AdminCommandServiceTests {

    @Autowired
    private PostReportCommandRepository postReportCommandRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminCommandService adminCommandService;

    @Autowired
    private PostReportCommandService postReportCommandService;

    @Test
    @DisplayName("게시글 신고 수락 테스트: 관리자가 신고 수락한 경우 신고 status 'ACCEPTED'로 변경 확인")
    void checkAcceptedStatus() {

        // given
        postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설",
                UUID.randomUUID(), UUID.randomUUID(), 11L));

        PostReportProcessingRequest postReportProcessingDTO = new PostReportProcessingRequest(true, 1L);

        adminCommandService.updatePostReportStatus(postReportProcessingDTO);

        // when
        PostReport updatedPostReport = postReportCommandRepository.findById(1L).get();

        // then
        Assertions.assertEquals(ProcessingStatus.ACCEPTED, updatedPostReport.getProcessingStatus());
    }

    @Test
    @DisplayName("게시글 신고 수락 테스트: 관리자가 신고 수락 시 게시글 상태값 true(조회 안됨) 변경")  // userPost merge 후 작성
    void updatePostStatusTrue() {


    }

    @Test
    @DisplayName("게시글 신고 수락 테스트: 관리자가 게시글 신고 수락한 경우 유저 신고횟수 +1")
    void checkUserReportCount() {

        // given
        User reportedUser = new User("account", "pass123", "김철수", "닉네임", "abc@abc",
                "asd", "dd", 1, false, true, Role.USER, "aa", "aa");

        userRepository.save(reportedUser);

        PostReport postReport = postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설",
                UUID.randomUUID(), reportedUser.getId(), 11L));

        PostReportProcessingRequest postReportProcessingDTO = new PostReportProcessingRequest(true, postReport.getPostReportNo());

        // when
        int newReportCount = adminCommandService.addReportCountToUser(postReportProcessingDTO);

        // then
        Assertions.assertEquals(2, newReportCount);
    }

    @Test
    @DisplayName("게시글 신고 거절 테스트: 관리자가 신고 거절한 경우 신고 status 'REJECTED'로 변경 확인")
    void checkRejectedStatus() {

        // given
        postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설",
                        UUID.randomUUID(), UUID.randomUUID(), 11L));

        PostReportProcessingRequest postReportProcessingDTO = new PostReportProcessingRequest(false, 1L);

        adminCommandService.updatePostReportStatus(postReportProcessingDTO);

        // when
        PostReport updatedPostReport = postReportCommandRepository.findById(1L).get();

        // then
        Assertions.assertEquals(ProcessingStatus.REJECTED, updatedPostReport.getProcessingStatus());
    }

    @Test
    @DisplayName("게시글 신고 거절 테스트: 관리자가 신고 거절 시 게시글 상태값 false(조회 됨) 유지")  // userPost merge 후 작성
    void checkPostStatusFalse() {


    }

    @Test
    @DisplayName("게시글 신고 거절 테스트: 관리자가 게시글 신고 거절한 경우 유저 신고횟수 동일 확인")
    void checkSameUserReportCount() {

        // given
        User reportedUser = new User("account", "pass123", "김철수", "닉네임", "abc@abc",
                "asd", "dd", 1, false, true, Role.USER, "aa", "aa");

        userRepository.save(reportedUser);

        PostReport postReport = postReportCommandService.savePostReport(new PostReportSaveRequest(1, "욕설",
                UUID.randomUUID(), reportedUser.getId(), 11L));

        PostReportProcessingRequest postReportProcessingDTO = new PostReportProcessingRequest(false, postReport.getPostReportNo());

        // when
        int newReportCount = adminCommandService.addReportCountToUser(postReportProcessingDTO);

        // then
        Assertions.assertEquals(1, newReportCount);
    }

    @Test
    @DisplayName("블랙리스트 변경 테스트: 유저 신고횟수 5회 이상인 경우 블랙리스트 상태값 true 지정")
    void updateBlackListStatus() {

        // given
        User user = new User("account", "pass123", "김철수", "닉네임", "abc@abc",
                "asd", "dd", 5, false, true, Role.USER, "aa", "aa");

        userRepository.save(user);

        // when
        boolean blackListStatus = adminCommandService.updateBlackListStatus(user.getId());

        // then
        Assertions.assertEquals(true, blackListStatus);

    }
}

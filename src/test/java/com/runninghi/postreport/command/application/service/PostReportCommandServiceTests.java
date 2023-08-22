package com.runninghi.postreport.command.application.service;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.postreport.command.application.dto.request.PostReportRequest;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import com.runninghi.postreport.command.domain.repository.PostReportCommandRepository;
import com.runninghi.postreport.query.application.service.PostReportQueryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class PostReportCommandServiceTests {

    @Autowired
    private PostReportCommandRepository postReportCommandRepository;

    @Autowired
    private PostReportCommandService postReportCommandService;

    @Autowired
    private PostReportQueryService postReportQueryService;

    @Test
    @DisplayName("게시글 신고 저장 테스트: DTO 엔티티 변환 후 저장 확인")
    void savePostReportTest() {

        //given
        Long before = postReportCommandRepository.count();

        UUID reportUserNo = UUID.randomUUID();
        UUID reportedUserNo = UUID.randomUUID();
        Long reportedPostNo = 1L;

        //when
        PostReportRequest postReportRequest = new PostReportRequest(1, "욕설");

        PostReport postReport = PostReport.builder()
                .postReportCategoryCode(postReportRequest.postReportCategoryCode())
                .postReportContent(postReportRequest.postReportContent())
                .postReportedDate(LocalDateTime.now())
                .postReportUserVO(new PostReportUserVO(reportUserNo))
                .postReportedUserVO(new PostReportedUserVO(reportedUserNo))
                .reportedPostVO(new ReportedPostVO(reportedPostNo))
                .build();

        postReportCommandRepository.save(postReport);
        Long after = postReportCommandRepository.count();

       //then
        Assertions.assertEquals(1, after - before);
    }

    @Test
    @DisplayName("게시글 신고 저장 테스트: 카테고리 미선택 시 저장 안하는지 확인")
    void checkPostReportCategoryCodeTest() {

        //given
        Long before = postReportCommandRepository.count();

        PostReportRequest postReportRequest =
                new PostReportRequest(0, "욕설");

        //when
        // 필기. 예외의 유형, () -> 테스트할 코드
        Assertions.assertThrows(IllegalArgumentException.class, () -> postReportCommandService.savePostReport(postReportRequest,
                UUID.randomUUID(), UUID.randomUUID(), 1L));

        Long after = postReportCommandRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }

    @Test
    @DisplayName("게시글 신고 저장 테스트: 신고 내용 공백일 시 저장 안하는지 확인")
    void checkPostReportContentTest() {

        //given
        Long before = postReportCommandRepository.count();

        PostReportRequest postReportRequest =
                new PostReportRequest(1, "");

        //when
        Assertions.assertThrows(IllegalArgumentException.class, () -> postReportCommandService.savePostReport(postReportRequest,
                UUID.randomUUID(), UUID.randomUUID(), 1L));

        Long after = postReportCommandRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }

    @Test
    @DisplayName("게시글 신고 저장 테스트: 신고 내용 100자 초과일 시 저장 안하는지 확인")
    void checkPostReportContentLengthTest() {

        //given
        Long before = postReportCommandRepository.count();

        String str = "a".repeat(101);

        PostReportRequest postReportRequest =
                new PostReportRequest(1, str);

        //when
        assertThatThrownBy(() -> postReportCommandService.savePostReport(postReportRequest,
                UUID.randomUUID(), UUID.randomUUID(), 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("신고 내용은 100자를 넘을 수 없습니다.");

        Long after = postReportCommandRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }

    @Test
    @DisplayName("게시글 신고 삭제 테스트: 삭제 성공 확인")
    void deletePostReportTest() {

        //given
        PostReportRequest postReportRequest = new PostReportRequest(2, "홍보 게시물");
        PostReport savedPostReport = postReportCommandService.savePostReport(postReportRequest,
                UUID.randomUUID(), UUID.randomUUID(), 1L);

        //when
        postReportCommandService.deletePostReport(savedPostReport.getPostReportNo());

        //then
        assertThatThrownBy(() -> postReportQueryService.findPostReport(savedPostReport.getPostReportNo()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당하는 신고 내역이 없습니다.");
    }

    @Test
    @DisplayName("게시글 신고 삭제 테스트: 상세 조회 시 해당하는 신고 내역 없을 시 예외처리 확인")
    void doesNotExistPostReportTest() {

        //given
        PostReportRequest postReportRequest = new PostReportRequest(2, "홍보 게시물");
        PostReport savedPostReport = postReportCommandService.savePostReport(postReportRequest,
                UUID.randomUUID(), UUID.randomUUID(), 1L);

        Long before = postReportCommandRepository.count();

        //when
        assertThatThrownBy(() -> postReportCommandService.deletePostReport(10L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당하는 신고 내역이 없습니다.");

        Long after = postReportCommandRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }



}

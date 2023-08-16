package com.runninghi.postreport.command.application.service;

import com.runninghi.feedback.command.domain.exception.customException.NotFoundException;
import com.runninghi.postreport.command.application.dto.RequestPostReportDTO;
import com.runninghi.postreport.command.application.dto.ResponsePostReportDTO;
import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import com.runninghi.postreport.command.domain.repository.PostReportRepository;
import org.aspectj.weaver.ast.Instanceof;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class PostReportServiceTests {

    @Autowired
    private PostReportRepository postReportRepository;

    @Autowired
    private PostReportService postReportService;

    @Test
    @DisplayName("게시글 신고 저장 테스트 - DTO 엔티티 변환 후 저장 확인")
    void savePostReportTest() {

        //given
        Long before = postReportRepository.count();

        UUID reportUserNo = UUID.randomUUID();
        UUID reportedUserNo = UUID.randomUUID();
        Long reportedPostNo = 1L;

        //when
        RequestPostReportDTO requestPostReportDTO = new RequestPostReportDTO(1, "욕설");

        PostReport postReport = PostReport.builder()
                .postReportCategoryCode(requestPostReportDTO.getPostReportCategoryCode())
                .postReportContent(requestPostReportDTO.getPostReportContent())
                .postReportedDate(LocalDate.now())
                .postReportUserVO(new PostReportUserVO(reportUserNo))
                .postReportedUserVO(new PostReportedUserVO(reportedUserNo))
                .reportedPostVO(new ReportedPostVO(reportedPostNo))
                .build();

        postReportRepository.save(postReport);
        Long after = postReportRepository.count();

       //then
        Assertions.assertEquals(1, after - before);
    }

    @Test
    @DisplayName("게시글 신고 저장 테스트 - 카테고리 미선택 시 저장 안하는지 확인")
    void checkPostReportCategoryCodeTest() {

        //given
        Long before = postReportRepository.count();

        RequestPostReportDTO requestPostReportDTO =
                new RequestPostReportDTO(0, "욕설");

        //when
        // 필기. 예외의 유형, () -> 테스트할 코드
        Assertions.assertThrows(IllegalArgumentException.class, () -> postReportService.savePostReport(requestPostReportDTO));

        Long after = postReportRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }

    @Test
    @DisplayName("게시글 신고 저장 테스트 - 신고 내용 null일 시 저장 안하는지 확인")
    void checkPostReportContentTest() {

        //given
        Long before = postReportRepository.count();

        RequestPostReportDTO requestPostReportDTO =
                new RequestPostReportDTO(1, null);

        //when
        Assertions.assertThrows(IllegalArgumentException.class, () -> postReportService.savePostReport(requestPostReportDTO));

        Long after = postReportRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }

    @Test
    @DisplayName("게시글 신고 저장 테스트 - 신고 내용 100자 초과일 시 저장 안하는지 확인")
    void checkPostReportContentLengthTest() {

        //given
        Long before = postReportRepository.count();

        String str = "a".repeat(101);

        RequestPostReportDTO requestPostReportDTO =
                new RequestPostReportDTO(1, str);

        //when
        assertThatThrownBy(() -> postReportService.savePostReport(requestPostReportDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("신고 내용은 100자를 넘을 수 없습니다.");

        Long after = postReportRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }
    @Test
    @DisplayName("게시글 신고 조회 테스트")
    void findPostReportTest() {

        //given
        RequestPostReportDTO requestPostReportDTO = new RequestPostReportDTO(2, "홍보 게시물");

        //when
        PostReport savedPostReport = postReportService.savePostReport(requestPostReportDTO);

        PostReport findedPostReport = postReportRepository.findById(savedPostReport.getPostReportNo()).get();

        //then
        assertThat(findedPostReport.getPostReportContent().equals("홍보 게시물"));
        assertThat(findedPostReport.getPostReportCategoryCode()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 신고 삭제 테스트 - 해당하는 신고 내역 존재하는 경우 삭제 확인")
    void deletePostReportTest() {

        //given
        RequestPostReportDTO requestPostReportDTO = new RequestPostReportDTO(2, "홍보 게시물");
        PostReport savedPostReport = postReportService.savePostReport(requestPostReportDTO);

        Long before = postReportRepository.count();

        //when
        postReportService.deletePostReport(savedPostReport.getPostReportNo());

        Long after = postReportRepository.count();

        //then
        Assertions.assertEquals(-1, after - before);
    }

    @Test
    @DisplayName("게시글 신고 삭제 테스트 - 해당하는 신고 내역 없을 시 예외처리 확인")
    void checkPostReportNoExistTest() {

        //given
        RequestPostReportDTO requestPostReportDTO = new RequestPostReportDTO(2, "홍보 게시물");
        PostReport savedPostReport = postReportService.savePostReport(requestPostReportDTO);

        Long before = postReportRepository.count();

        //when
        assertThatThrownBy(() -> postReportService.deletePostReport(10L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당하는 신고 내역이 없습니다.");

        Long after = postReportRepository.count();

        //then
        Assertions.assertEquals(after, before);
    }

}

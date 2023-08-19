package com.runninghi.postreport.command.domain.aggregate.entity;

import com.runninghi.postreport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_POST_REPORT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@ToString
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postReportNo;
    @Column
    private int postReportCategoryCode;     // 신고 카테고리 번호
    @Column
    private String postReportContent;       // 신고 내용
    @Column
    private LocalDateTime postReportedDate;     // 신고 일시
    @Embedded
    private PostReportUserVO postReportUserVO;      // 신고자 번호
    @Embedded
    private PostReportedUserVO postReportedUserVO;      // 피신고자 번호
    @Embedded
    private ReportedPostVO reportedPostVO;      // 신고된 게시글 번호

    @Builder
    public PostReport(Long postReportNo, int postReportCategoryCode, String postReportContent, LocalDateTime postReportedDate, PostReportUserVO postReportUserVO, PostReportedUserVO postReportedUserVO, ReportedPostVO reportedPostVO) {
        this.postReportNo = postReportNo;
        this.postReportCategoryCode = postReportCategoryCode;
        this.postReportContent = postReportContent;
        this.postReportedDate = postReportedDate;
        this.postReportUserVO = postReportUserVO;
        this.postReportedUserVO = postReportedUserVO;
        this.reportedPostVO = reportedPostVO;
    }
}


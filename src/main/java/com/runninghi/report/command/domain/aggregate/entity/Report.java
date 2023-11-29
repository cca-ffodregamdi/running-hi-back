package com.runninghi.report.command.domain.aggregate.entity;

import com.runninghi.report.command.application.dto.request.ReportUpdateRequest;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ReportType;
import com.runninghi.report.command.domain.aggregate.vo.ReportedContentVO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_REPORT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@ToString
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportNo;
    @Enumerated(EnumType.STRING)
    @Column
    private ReportType reportType;    // 신고 유형(게시글/댓글)
    @Column
    private int reportCategoryCode;     // 신고 카테고리 번호
    @Column
    private String reportContent;       // 신고 내용
    @Column
    private String reportedDate;     // 신고 일시
    @Enumerated(EnumType.STRING)
    @Column
    private ProcessingStatus processingStatus;       // 신고 처리 상태(관리자)
//    @Embedded
//    private ReportUserVO reportUserVO;      // 신고자 번호
//    @Embedded
//    private ReportedUserVO reportedUserVO;      // 피신고자 번호
    @Embedded
    private ReportedContentVO reportedContentVO;      // 신고된 게시글 번호 -> 댓글 컬럼 추가하거나 같이쓰기

    @Builder
    public Report(ReportType reportType, int reportCategoryCode, String reportContent, String reportedDate, ProcessingStatus processingStatus) {
        this.reportType = reportType;
        this.reportCategoryCode = reportCategoryCode;
        this.reportContent = reportContent;
        this.reportedDate = reportedDate;
        this.processingStatus = processingStatus;
    }

//    public Report(int reportCategoryCode, String reportContent, LocalDateTime reportedDate, ProcessingStatus processingStatus, ReportUserVO reportUserVO, ReportedUserVO reportedUserVO, ReportedContentVO reportedContentVO) {
//        this.reportCategoryCode = reportCategoryCode;
//        this.reportContent = reportContent;
//        this.reportedDate = reportedDate;
//        this.processingStatus = processingStatus;
//        this.reportUserVO = reportUserVO;
//        this.reportedUserVO = reportedUserVO;
//        this.reportedContentVO = reportedContentVO;
//    }


    public void update(ReportUpdateRequest reportUpdateRequest) {
        this.processingStatus = reportUpdateRequest.processingStatus();
    }
}


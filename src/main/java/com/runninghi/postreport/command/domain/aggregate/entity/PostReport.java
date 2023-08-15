package com.runninghi.postreport.command.domain.aggregate.entity;

import com.runninghi.postreport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

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
    private int postReportCategoryCode;
    @Column
    private String postReportContent;
    @Column
    private LocalDate postReportedDate;
    @Embedded
    private PostReportUserVO postReportUserVO;
    @Embedded
    private PostReportedUserVO postReportedUserVO;
    @Embedded
    private ReportedPostVO reportedPostVO;

    @Builder
    public PostReport(Long postReportNo, int postReportCategoryCode, String postReportContent, LocalDate postReportedDate, PostReportUserVO postReportUserVO, PostReportedUserVO postReportedUserVO, ReportedPostVO reportedPostVO) {
        this.postReportNo = postReportNo;
        this.postReportCategoryCode = postReportCategoryCode;
        this.postReportContent = postReportContent;
        this.postReportedDate = postReportedDate;
        this.postReportUserVO = postReportUserVO;
        this.postReportedUserVO = postReportedUserVO;
        this.reportedPostVO = reportedPostVO;
    }
}


package com.runninghi.postReport.command.domain.aggregate.entity;

import com.runninghi.postReport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postReport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postReport.command.domain.aggregate.vo.ReportedPostVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "TBL_POST_REPORT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postReportNo;
    @Column
    private int postReportCategoryCode;
    @Column
    private String postReportContent;
    @Column
    private Timestamp postReportedDate;
    @Embedded
    private PostReportUserVO postReportUserVO;
    @Embedded
    private PostReportedUserVO postReportedUserVO;
    @Embedded
    private ReportedPostVO reportedPostVO;

    public PostReport(Long postReportNo, int postReportCategoryCode, String postReportContent, Timestamp postReportedDate, PostReportUserVO postReportUserVO, PostReportedUserVO postReportedUserVO, ReportedPostVO reportedPostVO) {
        this.postReportNo = postReportNo;
        this.postReportCategoryCode = postReportCategoryCode;
        this.postReportContent = postReportContent;
        this.postReportedDate = postReportedDate;
        this.postReportUserVO = postReportUserVO;
        this.postReportedUserVO = postReportedUserVO;
        this.reportedPostVO = reportedPostVO;
    }
}


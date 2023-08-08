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
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postReportNo;
    @Column
    int postReportCategoryCode;
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
}

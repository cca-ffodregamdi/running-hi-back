package com.runninghi.postreport.command.domain.aggregate.entity;

import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private PostReportedUserVO postReportedUserVO;
    @Embedded
    private ReportedPostVO reportedPostVO;
}


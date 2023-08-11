package com.runninghi.commentreport.command.domain.aggregate.entity;

import com.runninghi.commentreport.command.domain.aggregate.vo.CommentReportUserVO;
import com.runninghi.commentreport.command.domain.aggregate.vo.CommentReportedUserVO;
import com.runninghi.commentreport.command.domain.aggregate.vo.ReportedCommentVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "TBL_COMMENT_REPORT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentReportNo;
    @Column
    private int commentReportCategoryCode;
    @Column
    private String commentReportContent;
    @Column
    private Timestamp commentReportedDate;
    @Embedded
    private CommentReportUserVO commentReportUserVO;
    @Embedded
    private CommentReportedUserVO commentReportedUserVO;
    @Embedded
    private ReportedCommentVO reportedCommentVO;
}

package com.runninghi.commentReport.command.domain.aggregate.entity;

import com.runninghi.commentReport.command.domain.aggregate.vo.CommentReportUserVO;
import com.runninghi.commentReport.command.domain.aggregate.vo.CommentReportedUserVO;
import com.runninghi.commentReport.command.domain.aggregate.vo.ReportedCommentVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
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

    public CommentReport(Long commentReportNo, int commentReportCategoryCode, String commentReportContent, Timestamp commentReportedDate, CommentReportUserVO commentReportUserVO, CommentReportedUserVO commentReportedUserVO, ReportedCommentVO reportedCommentVO) {
        this.commentReportNo = commentReportNo;
        this.commentReportCategoryCode = commentReportCategoryCode;
        this.commentReportContent = commentReportContent;
        this.commentReportedDate = commentReportedDate;
        this.commentReportUserVO = commentReportUserVO;
        this.commentReportedUserVO = commentReportedUserVO;
        this.reportedCommentVO = reportedCommentVO;
    }
}

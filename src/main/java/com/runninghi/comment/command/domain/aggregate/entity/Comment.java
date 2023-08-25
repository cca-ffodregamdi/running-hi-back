package com.runninghi.comment.command.domain.aggregate.entity;

import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import com.runninghi.comment.command.domain.aggregate.vo.CommentUserVO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    @Embedded
    private CommentUserVO userNo;

    @Column
    private Long userPostNo;

    @Column
    private Date commentDate;

    @Column
    private String commentContent;

    @Column
    private int commentReportCnt;

    @Builder
    public Comment(Long commentNo, CommentUserVO userNo, Long userPostNo, Date commentDate, String commentContent, int commentReportCnt) {
        this.commentNo = commentNo;
        this.userNo = userNo;
        this.userPostNo = userPostNo;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
        this.commentReportCnt = commentReportCnt;
    }

    public void update(UpdateCommentRequest commentRequest) {
        this.commentContent = commentRequest.commentContent();
        this.commentDate = new Date();
    }
}

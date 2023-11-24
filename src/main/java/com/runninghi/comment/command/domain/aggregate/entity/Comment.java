package com.runninghi.comment.command.domain.aggregate.entity;

import com.runninghi.comment.command.application.dto.request.UpdateCommentRequest;
import com.runninghi.comment.command.domain.aggregate.vo.CommentMemberVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private CommentMemberVO memberNoVO;

    @Column
    private Long memberPostNo;

    @Column
    private Date commentDate;

    @Column
    private String commentContent;

    @Column
    private int commentReportCnt;

    @Column(nullable = false)
    private boolean commentStatus;

    @Column
    private Date updateDate;

    @Builder
    public Comment(Long commentNo, CommentMemberVO memberNoVO, Long memberPostNo, Date commentDate, String commentContent, int commentReportCnt, boolean commentStatus, Date updateDate) {
        this.commentNo = commentNo;
        this.memberNoVO = memberNoVO;
        this.memberPostNo = memberPostNo;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
        this.commentReportCnt = commentReportCnt;
        this.commentStatus = commentStatus;
        this.updateDate = updateDate;
    }

    public void update(UpdateCommentRequest commentRequest) {
        this.commentContent = commentRequest.commentContent();
        this.updateDate = new Date();
    }
}

package com.runninghi.commentreport.command.domain.aggregate.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ReportedCommentVO implements Serializable {

    @Column
    private Long reportedCommentNo;

    public ReportedCommentVO(Long reportedCommentNo) {
        this.reportedCommentNo = reportedCommentNo;
    }
}

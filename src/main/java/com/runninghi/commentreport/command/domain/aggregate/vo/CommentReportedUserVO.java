package com.runninghi.commentreport.command.domain.aggregate.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CommentReportedUserVO implements Serializable {

    @Column
    private Long commentReportedUserNo;

    public CommentReportedUserVO(Long commentReportedUserNo) {
        this.commentReportedUserNo = commentReportedUserNo;
    }
}

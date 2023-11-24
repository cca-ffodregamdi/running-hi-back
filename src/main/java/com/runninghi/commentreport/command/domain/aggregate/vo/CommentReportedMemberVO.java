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
public class CommentReportedMemberVO implements Serializable {

    @Column
    private Long commentReportedUserNo;

    public CommentReportedMemberVO(Long commentReportedUserNo) {
        this.commentReportedUserNo = commentReportedUserNo;
    }
}

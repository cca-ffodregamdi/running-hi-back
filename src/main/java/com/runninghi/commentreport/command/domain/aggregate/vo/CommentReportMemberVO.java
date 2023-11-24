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
public class CommentReportMemberVO implements Serializable {

    @Column
    private Long commentReportUserNo;

    public CommentReportMemberVO(Long commentReportUserNo) {
        this.commentReportUserNo = commentReportUserNo;
    }
}

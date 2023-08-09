package com.runninghi.comment.command.domain.aggregate.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Embeddable
public class CommentReportCntVO {

    @Column
    private int commentReportCnt;

    //신고 횟수 관련 로직 추가

}

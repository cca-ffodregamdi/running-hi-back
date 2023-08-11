package com.runninghi.comment.command.domain.aggregate.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Embeddable
public class CommentReportCntVO {

    @Column
    private int commentReportCnt;

    //신고 횟수 관련 로직 추가

}

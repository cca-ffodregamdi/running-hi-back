package com.runninghi.postreport.command.domain.aggregate.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PostReportUserVO implements Serializable {

    @Column
    private Long postReportUserNo;

    public PostReportUserVO(Long postReportUserNo) {
        this.postReportUserNo = postReportUserNo;
    }
}

package com.runninghi.report.command.domain.aggregate.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@ToString
public class ReportedContentVO implements Serializable {

    @Column
    private Long reportedContentNo;

    public ReportedContentVO(Long reportedPostNo) {
        this.reportedContentNo = reportedContentNo;
    }
}

package com.runninghi.report.command.domain.aggregate.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@ToString
public class ReportedUserVO implements Serializable {

    @Column
    private UUID reportedUserNo;

    public ReportedUserVO(UUID reportedUserNo) {
        this.reportedUserNo = reportedUserNo;
    }
}

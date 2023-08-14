package com.runninghi.postreport.command.domain.aggregate.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ReportedPostVO implements Serializable {

    @Column
    private Long reportedPostNo;

    public ReportedPostVO(Long reportedPostNo) {
        this.reportedPostNo = reportedPostNo;
    }
}

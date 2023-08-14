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
public class PostReportedUserVO implements Serializable {

    @Column
    private Long postReportedUserNo;

    public PostReportedUserVO(Long postReportedUserNo) {
        this.postReportedUserNo = postReportedUserNo;
    }
}

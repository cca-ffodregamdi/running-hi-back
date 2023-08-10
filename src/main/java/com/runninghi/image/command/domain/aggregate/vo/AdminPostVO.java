package com.runninghi.image.command.domain.aggregate.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AdminPostVO {

    @Column
    private Long adminPostNo;
}

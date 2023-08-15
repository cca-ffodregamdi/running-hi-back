package com.runninghi.image.command.domain.aggregate.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AdminPostVO {

    @Column
    private Long adminPostNo;
}

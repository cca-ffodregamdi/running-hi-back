package com.runninghi.image.command.domain.aggregate.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserPostVO {

    @Column
    private Long userPostNo;
}

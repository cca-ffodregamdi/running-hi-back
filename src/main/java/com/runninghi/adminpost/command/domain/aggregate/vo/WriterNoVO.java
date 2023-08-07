package com.runninghi.adminpost.command.domain.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WriterNoVO {

    @Column
    private Long writerNo;
}

package com.runninghi.postreport.command.domain.aggregate.entity.enumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProcessingStatus {

    INPROGRESS("처리중"),
    ACCEPTED("수락됨"),
    REJECTED("거절됨");

    private final String status;

}

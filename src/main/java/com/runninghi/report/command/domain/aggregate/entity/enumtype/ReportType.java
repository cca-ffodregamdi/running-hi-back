package com.runninghi.report.command.domain.aggregate.entity.enumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {

    POST("게시글"),
    COMMENT("댓글");

    private final String type;
}

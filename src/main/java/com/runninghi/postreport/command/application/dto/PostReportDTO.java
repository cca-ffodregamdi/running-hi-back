package com.runninghi.postreport.command.application.dto;

import com.runninghi.postreport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@ToString
public class PostReportDTO {

    private Long postReportNo;
    private int postReportCategoryCode;
    private String postReportContent;
    private Timestamp postReportedDate;
    private PostReportUserVO postReportUserVO;
    private PostReportedUserVO postReportedUserVO;
    private ReportedPostVO reportedPostVO;
}

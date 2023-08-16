package com.runninghi.postreport.command.application.dto;

import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.PostReportedUserVO;
import com.runninghi.postreport.command.domain.aggregate.vo.ReportedPostVO;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostReportDTO {

    private Long postReportNo;
    private int postReportCategoryCode;
    private String postReportContent;
    private LocalDate postReportedDate;
    private UUID postReportUserNo;
    private UUID postReportedUserNo;
    private Long reportedPostNo;

    public ResponsePostReportDTO(PostReport postReport) {

        postReportNo = postReport.getPostReportNo();
        postReportCategoryCode = postReport.getPostReportCategoryCode();
        postReportContent = postReport.getPostReportContent();
        postReportedDate = postReport.getPostReportedDate();
        postReportUserNo = postReport.getPostReportUserVO().getPostReportUserNo();
        postReportedUserNo = postReport.getPostReportedUserVO().getPostReportedUserNo();
        reportedPostNo = postReport.getReportedPostVO().getReportedPostNo();
    }
}

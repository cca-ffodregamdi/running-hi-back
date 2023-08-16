package com.runninghi.postreport.command.application.dto;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostReportDTO {

    private int postReportCategoryCode;
    private String postReportContent;
}

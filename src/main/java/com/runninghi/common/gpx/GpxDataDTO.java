package com.runninghi.common.gpx;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GpxDataDTO {

    private Long adminPostNo;
    private Long userPostNo;
    private Long userNo;
    private double latitude;
    private double longitude;
    private double elevation;
    private double distance;
    private double slope;
    private String measureTime;
    private int timeDifference;
}

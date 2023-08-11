package com.runninghi.common;

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
    private double slopre;
    private String measureTime;
    private int timeDiff;
}

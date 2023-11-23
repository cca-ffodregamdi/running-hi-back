package com.runninghi.adminpost.command.application.dto;

import com.runninghi.common.gpx.GpxDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRecordDTO {

    List<GpxDataDTO> points;
    double totalDistance;
    double totalTime;
    double maxSlope;
    double kcal;
}

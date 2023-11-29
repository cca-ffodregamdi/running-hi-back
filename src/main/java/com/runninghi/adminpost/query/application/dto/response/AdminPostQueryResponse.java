package com.runninghi.adminpost.query.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminPostQueryResponse {

    Long adminPostNo;
    UUID userKey;
    String adminPostThumbnailUrl;
    String adminPostTitle;
    double totalDistance;
    double totalTime;
    double maxSlope;
    double kcal;
    String createDate;
    String updateDate;
}

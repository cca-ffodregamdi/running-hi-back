package com.runninghi.adminpost.command.domain.service;

import com.runninghi.adminpost.command.application.dto.CourseRecordDTO;
import com.runninghi.common.annotation.DomainService;
import com.runninghi.common.gpx.GpxDataDTO;
import com.runninghi.common.gpx.GpxParser;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@DomainService
public class AdminPostCommandDomainService {

    private final GpxParser gpxParser;

    public void checkAdmin(Role apiResult) {    // 추후에 User 도메인으로 이동 예정
        int checkAdmin = apiResult.compareTo(Role.ADMIN);
        if (checkAdmin != 0) {
            throw new IllegalArgumentException("관리자만 생성이 가능합니다.");
        }
    }

    public CourseRecordDTO extractGPX(MultipartFile multipartGpxFile) throws IOException {

        File gpxFile = convertMultipartFileToFile(multipartGpxFile);

        List<GpxDataDTO> points = gpxParser.parseGPXFile(gpxFile);  // mongoDB에 저장 필요.
        double totalDistance = 0;
        double totalTime = 0;
        double maxSlope = 0;

        for( GpxDataDTO point : points) {

            totalDistance += point.getDistance();
            totalTime += point.getTimeDifference();

            if (maxSlope < point.getSlope()) {
                maxSlope = point.getSlope();
            }
        }

        double kcal = ((3.5/60)*7*60*totalTime/1000)*5; // ((산소3.5ml/60s) * met(s) * 몸무게(kg) * 운동시간(s)) * 산소 1L 당 5kcal, 조깅 met(s)= 7

        gpxFile.delete();

        return new CourseRecordDTO(
                points,
                totalDistance,
                totalTime,
                maxSlope,
                kcal
        );

    }

    private File convertMultipartFileToFile (MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }
}

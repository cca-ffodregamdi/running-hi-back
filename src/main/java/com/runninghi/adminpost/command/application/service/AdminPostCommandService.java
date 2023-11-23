package com.runninghi.adminpost.command.application.service;

import com.runninghi.adminpost.command.application.dto.CourseRecordDTO;
import com.runninghi.adminpost.command.application.dto.request.AdminPostRequestDTO;
import com.runninghi.adminpost.command.application.dto.response.AdminPostResponse;
import com.runninghi.adminpost.command.domain.aggregate.document.AdminCourse;
import com.runninghi.adminpost.command.domain.aggregate.entity.AdminPost;
import com.runninghi.adminpost.command.domain.aggregate.vo.WriterNoVO;
import com.runninghi.adminpost.command.domain.mongo.AdminCourseCommandRepository;
import com.runninghi.adminpost.command.domain.repository.AdminPostCommandRepository;
import com.runninghi.adminpost.command.domain.service.AdminPostCommandDomainService;
import com.runninghi.adminpost.command.domain.service.ApiAdminPostDomainService;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminPostCommandService {

    private final AdminPostCommandDomainService adminPostCommandDomainService;
    private final ApiAdminPostDomainService apiAdminPostDomainService;
    private final AdminPostCommandRepository adminPostCommandRepository;
    private final AdminCourseCommandRepository adminCourseCommandRepository;

    public void checkAdminByUserNo(UUID userKey) {
        Role apiResult = apiAdminPostDomainService.checkAdminByUserNo(userKey);
        adminPostCommandDomainService.checkAdmin(apiResult);
    }

    @Transactional
    public AdminPostResponse createAdminPost(AdminPostRequestDTO request) throws IOException {

        checkAdminByUserNo(request.getUserKey());   // 관리자인지 체크

        String thumbnailUrl = apiAdminPostDomainService.uploadThumbnail(request.getThumbnail()); // 썸네일 클라우드에 업로드

        // gpx 파일 내에서 기록 추출
        CourseRecordDTO courseRecord = adminPostCommandDomainService.extractGPX(request.getGpxFile());

        // adminPost 저장
        AdminPost adminPost = adminPostCommandRepository.save(
                AdminPost.builder()
                        .writerNoVO(new WriterNoVO(request.getUserKey()))
                        .adminPostTitle(request.getAdminPostTitle())
                        .adminPostContent(request.getAdminPostContent())
                        .adminPostThumbnailUrl(thumbnailUrl)
                        .totalDistance(courseRecord.getTotalDistance())
                        .totalTime(courseRecord.getTotalTime())
                        .maxSlope(courseRecord.getMaxSlope())
                        .kcal(courseRecord.getKcal())
                        .createDate(new Date().toString())
                        .build()
        );

        // 좌표들 MongoDB에 저장
        AdminCourse adminCourse = adminCourseCommandRepository.save(
                AdminCourse.builder()
                        .adminPostNo(adminPost.getAdminPostNo())
                        .points(courseRecord.getPoints())
                        .build()
        );

        // 게시물 키워드 저장
        List<Long> keywordNoList = apiAdminPostDomainService.createKeywordOfAdminPost(
                request.getKeywordList(),
                adminPost.getAdminPostNo()
        );

        return AdminPostResponse.of(
                adminPost.getWriterNoVO().getWriterNo(),
                adminPost.getAdminPostThumbnailUrl(),
                adminPost.getAdminPostTitle(),
                adminPost.getAdminPostContent(),
                keywordNoList
        );
    }

    @Transactional
    public AdminPostResponse updateAdminPost(Long adminPostNo, AdminPostRequestDTO request) throws IOException {

        // 관리자 체크
        checkAdminByUserNo(request.getUserKey());

        // 기존 엔티티 조회
        AdminPost adminPost = adminPostCommandRepository.findById(adminPostNo)
                .orElseThrow( () -> new IllegalArgumentException("일치하는 관리자 게시글이 없습니다."));

        // 클라우드에서 기존 썸네일 삭제
        apiAdminPostDomainService.deleteOldThumbnail(adminPost.getAdminPostThumbnailUrl());

        // 새 썸네일 클라우드 업로드
        String newThumbnailUrl = apiAdminPostDomainService.uploadThumbnail(request.getThumbnail());

        // gpx 파일 내에서 기록 추출
        CourseRecordDTO courseRecord = adminPostCommandDomainService.extractGPX(request.getGpxFile());

        // adminPost 업데이트
        adminPost.update(
                request.getAdminPostTitle(),
                request.getAdminPostContent(),
                newThumbnailUrl,
                courseRecord.getTotalDistance(),
                courseRecord.getTotalTime(),
                courseRecord.getMaxSlope(),
                courseRecord.getKcal()
        );

        // NoSQL에서 기존 코스 정보 삭제
        AdminCourse adminCourse = adminCourseCommandRepository.findByAdminPostNo(adminPostNo)
                        .orElseThrow( () -> new IllegalArgumentException("일치하는 코스가 존재하지 않습니다."));

        // NoSQL에서 새 코스 정보 업데이트
        adminCourse.update(courseRecord.getPoints());

        List<Long> keywordNoList = apiAdminPostDomainService.updateKeywordOfAdminPost(
                request.getKeywordList(),
                adminPostNo
        );

        return AdminPostResponse.of(
                adminPost.getWriterNoVO().getWriterNo(),
                adminPost.getAdminPostThumbnailUrl(),
                adminPost.getAdminPostTitle(),
                adminPost.getAdminPostContent(),
                keywordNoList
        );
    }

    public void deleteAdminPost(Long adminPostNo) {

        // 기존 엔티티 조회
        AdminPost adminPost = adminPostCommandRepository.findById(adminPostNo)
                .orElseThrow( () -> new IllegalArgumentException("일치하는 관리자 게시글이 없습니다."));

        // 클라우드에서 기존 썸네일 삭제
        apiAdminPostDomainService.deleteOldThumbnail(adminPost.getAdminPostThumbnailUrl());

        // NoSQL에서 기존 코스 정보 삭제
        AdminCourse adminCourse = adminCourseCommandRepository.findByAdminPostNo(adminPostNo)
                .orElseThrow( () -> new IllegalArgumentException("일치하는 코스가 존재하지 않습니다."));

        // 기존 엔티티 삭제
        adminPostCommandRepository.deleteById(adminPostNo);
    }
}

package com.runninghi.adminpost.query.application.service;

import com.runninghi.adminpost.command.domain.aggregate.entity.AdminPost;
import com.runninghi.adminpost.query.application.dto.request.QueryParameterRequest;
import com.runninghi.adminpost.query.application.dto.response.AdminPostQueryResponse;
import com.runninghi.adminpost.query.infrastructure.mongo.AdminCourseQueryRepository;
import com.runninghi.adminpost.query.infrastructure.repository.AdminPostMapper;
import com.runninghi.adminpost.query.infrastructure.repository.AdminPostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminPostQueryService {

    private final AdminPostQueryRepository adminPostQueryRepository;
    private final AdminPostMapper adminPostMapper;

//    public Page<AdminPostQueryResponse> getAdminPostsList(Pageable pageable) {
//
//        Page<AdminPostQueryResponse> adminPostResponse =  adminPostQueryRepository.findAllAdminPostResponse(pageable)
//                .orElseThrow( () -> new IllegalArgumentException("일치하는 결과가 없습니다."));
//
//
//        return adminPostResponse;
//    }

    public List<AdminPostQueryResponse> getAdminPostsList(QueryParameterRequest request) {
        List<AdminPost> adminPostsList = adminPostMapper.getAdminPostsList(request);
    }

    public Long getAdminPostTotalCnt() {
        return adminPostMapper.getAdminPostTotalCnt();
    }

}

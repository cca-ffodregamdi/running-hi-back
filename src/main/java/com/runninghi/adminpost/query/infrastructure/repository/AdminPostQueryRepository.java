package com.runninghi.adminpost.query.infrastructure.repository;

import com.runninghi.adminpost.command.domain.aggregate.entity.AdminPost;
import com.runninghi.adminpost.query.application.dto.response.AdminPostQueryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminPostQueryRepository extends JpaRepository<AdminPost, Long> {


    @Query("select new com.runninghi.adminpost.query.application.dto.response.AdminPostQueryResponse(" +
            "a.adminPostNo, a.writerKeyVO.userKey, a.adminPostThumbnailUrl, a.adminPostTitle, " +
            "a.totalDistance, a.totalTime, a.maxSlope, a.kcal, a.createDate, a.updateDate) from AdminPost a")
    Optional<Page<AdminPostQueryResponse>> findAllAdminPostResponse(Pageable pageable);
}

package com.runninghi.adminpost.query.infrastructure.repository;

import com.runninghi.adminpost.command.domain.aggregate.entity.AdminPost;
import com.runninghi.adminpost.query.application.dto.request.QueryParameterRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminPostMapper {
    Long getAdminPostTotalCnt();

    List<AdminPost> getAdminPostsList(QueryParameterRequest request);
}

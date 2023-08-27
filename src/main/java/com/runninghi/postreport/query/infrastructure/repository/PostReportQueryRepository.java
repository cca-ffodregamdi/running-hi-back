package com.runninghi.postreport.query.infrastructure.repository;

import com.runninghi.postreport.command.domain.aggregate.entity.PostReport;
import com.runninghi.postreport.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostReportQueryRepository extends JpaRepository<PostReport, Long> {
    List<PostReport> findByProcessingStatus(ProcessingStatus processingStatus);
}

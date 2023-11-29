package com.runninghi.report.query.infrastructure.repository;

import com.runninghi.report.command.domain.aggregate.entity.Report;
import com.runninghi.report.command.domain.aggregate.entity.enumtype.ProcessingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportQueryRepository extends JpaRepository<Report, Long> {
    List<Report> findByProcessingStatus(ProcessingStatus processingStatus);
}
